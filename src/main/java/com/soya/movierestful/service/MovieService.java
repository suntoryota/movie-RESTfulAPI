package com.soya.movierestful.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soya.movierestful.dto.MovieDto;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.exception.MovieNotFoundException;

@Service
public interface MovieService {

	
	Movie createMovie(MovieDto movieDto);
	
	List<Movie> getAllMovies();
	
	ResponseEntity<Movie> detailsMovie(int id) throws MovieNotFoundException; 
	
	Movie updateMovie(int id, MovieDto updateMovie) throws MovieNotFoundException;
	
	ResponseEntity<String> deleteMovie(int id) throws MovieNotFoundException;

	Optional<Movie> findByTitle(String title);
	
}
