package com.soya.movierestful.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.soya.movierestful.config.ErrorResponse;


@ControllerAdvice
public class MovieExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodValidation(MethodArgumentNotValidException mv){
		
		List<String> fieldErrors = mv.getBindingResult().getFieldErrors().stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		
		Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("status", HttpStatus.BAD_REQUEST.name());
			errorMap.put("statusCode", HttpStatus.BAD_REQUEST.value());
			errorMap.put("timeStamp", LocalDateTime.now());
			errorMap.put("errorMessage", fieldErrors);
		
		return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> MovieExistsException(MovieAlreadyExistsException mv) {
        ErrorResponse errorResponse = new ErrorResponse(mv.getMessage());
        errorResponse.setStatusCode(HttpStatus.OK.value());
        errorResponse.setStatus(HttpStatus.OK.name());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
	
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> MovieNotFoundException(MovieNotFoundException mv) {
        ErrorResponse errorResponse = new ErrorResponse(mv.getMessage());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.name());
        errorResponse.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
