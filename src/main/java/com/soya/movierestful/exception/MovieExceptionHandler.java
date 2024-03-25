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
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class MovieExceptionHandler {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
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
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MovieAlreadyExistsException.class)
	public ResponseEntity<Object> MovieExistsException(MovieAlreadyExistsException mv){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setStatusCode(HttpStatus.OK.value());
		errorDetails.setStatus(HttpStatus.OK.name());
		errorDetails.setErrorMessage(mv.getMessage());
		errorDetails.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorDetails,HttpStatus.OK);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<Object> MovieNotFoundException(MovieNotFoundException mv){
		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
		errorDetails.setStatus(HttpStatus.NOT_FOUND.name());
		errorDetails.setErrorMessage(mv.getMessage());
		errorDetails.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
}
