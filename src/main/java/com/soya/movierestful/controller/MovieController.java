package com.soya.movierestful.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soya.movierestful.config.ErrorResponse;
import com.soya.movierestful.dto.MovieDto;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.exception.MovieAlreadyExistsException;
import com.soya.movierestful.exception.MovieNotFoundException;
import com.soya.movierestful.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
    @GetMapping("/")
    public ResponseEntity<Object> getAllMovies() {
        try {
            List<Movie> movies = movieService.getAllMovies();
            return ResponseEntity.ok(movies);
        } catch (Exception e) { 
        	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
				    	  errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				          errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				          errorResponse.setTimeStamp(LocalDateTime.now());
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
	@GetMapping("/detail/{id}")
    public ResponseEntity<Object> detailsMovie(@PathVariable int id) {
        try {
            ResponseEntity<Movie> movie = movieService.detailsMovie(id); 
            return ResponseEntity.ok(movie);
        } catch (MovieNotFoundException e) {
          	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
				    	  errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				          errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				          errorResponse.setTimeStamp(LocalDateTime.now());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

	@PostMapping("/create")
    public ResponseEntity<Object> createMovie(@RequestBody @Valid MovieDto movieDto) {
        try {
            Movie createdMovie = movieService.createMovie(movieDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
        } catch (MovieAlreadyExistsException e) {
          	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
				    	  errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				          errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				          errorResponse.setTimeStamp(LocalDateTime.now());
        	return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMovie(@PathVariable int id, @RequestBody MovieDto updateMovieDto) {
        try {
            Movie updatedMovie = movieService.updateMovie(id, updateMovieDto);
            return ResponseEntity.ok(updatedMovie);
        } catch (MovieNotFoundException e) {
          	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
				    	  errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				          errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				          errorResponse.setTimeStamp(LocalDateTime.now());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable int id) {
    	 try {
             movieService.deleteMovie(id);
             return ResponseEntity.ok("Movie deleted successfully!");
         } catch (MovieNotFoundException e) {
           	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
				    	  errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				          errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				          errorResponse.setTimeStamp(LocalDateTime.now());
        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
         }
    }
    
	@GetMapping("/detail/title/{title}")
    public ResponseEntity<Object> detailsMovieByTitle(@PathVariable String title) {
        try {
            Optional<Movie> movies = movieService.findByTitle(title);  
            return ResponseEntity.ok(movies);
        } catch (MovieNotFoundException e) {
          	ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
				    	  errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				          errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
				          errorResponse.setTimeStamp(LocalDateTime.now());
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        }
    }
   
}
