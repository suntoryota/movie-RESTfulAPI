package com.soya.movierestful.controller;

import java.util.List;
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

import com.soya.movierestful.dto.MovieDto;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.exception.MovieAlreadyExistsException;
import com.soya.movierestful.exception.MovieNotFoundException;
import com.soya.movierestful.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private MovieService service;
	
	//Build Get List All Movie 
	@GetMapping("/")
	public ResponseEntity<List<Movie>> getAllMovies(){
		return ResponseEntity.ok(service.getAllMovies());
	}
	
	//Build Get Movie 
	@GetMapping("/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable int id) throws MovieNotFoundException{
		return ResponseEntity.ok(service.getMovieById(id));
	}
	
	//Create Add Movie 
	@PostMapping("/")
	public ResponseEntity<Object> saveMovie(@RequestBody @Valid MovieDto movieRequest){
//		return ResponseEntity.ok(service.SaveMovie(movieRequest));
	    try {
//	        service.SaveMovie(movieRequest);
			return ResponseEntity.ok(service.createMovie(movieRequest));
//	        return ResponseEntity.status(HttpStatus.CREATED).body("Movie created successfully!\n" + movieRequest);
	    } catch (MovieAlreadyExistsException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("This movie already exists!\n" + movieRequest);
	    }
	}

	//Build update Movie
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateEmployee(	@PathVariable("id") int id, 
													@RequestBody MovieDto updateMovie) throws MovieNotFoundException{
		
		Movie movie = service.updateMovie(id, updateMovie);
		return ResponseEntity.ok(movie);
	}
	
	//Build Delete Movie 
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable int id) throws MovieNotFoundException {
	    try {
	        service.deleteMovie(id);
	        return ResponseEntity.ok("Movie deleted successfully!");
	    } catch (MovieNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("The movie with ID : " + id + " not found!");
	    }
	}
}
