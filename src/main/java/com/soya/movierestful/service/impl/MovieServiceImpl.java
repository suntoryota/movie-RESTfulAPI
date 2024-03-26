package com.soya.movierestful.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soya.movierestful.config.ErrorResponse;
import com.soya.movierestful.dto.MovieDto;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.exception.MovieAlreadyExistsException;
import com.soya.movierestful.exception.MovieNotFoundException;
import com.soya.movierestful.repository.MovieRepository;
import com.soya.movierestful.service.MovieService;


@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository repository;

	@Override
	public Movie createMovie(MovieDto movieDto) {
		
		if (repository.existsByTitle(movieDto.getTitle())) {
		    throw new MovieAlreadyExistsException("Movie with title '" + movieDto.getTitle() + "' already exists!");
		}

        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setRating(movieDto.getRating());
        movie.setImage(movieDto.getImage());

		return repository.save(movie);
	}
	
	@Override
	public List<Movie> getAllMovies(){
		return repository.findAll();
	}
	
	@Override
	public ResponseEntity<Movie> detailsMovie(int id) {

	    Movie movie = repository.findById(id)
	            .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + id));

	    return ResponseEntity.ok(movie); 
	}
	
    @Override
    public Movie updateMovie(int id, MovieDto updateMovie) throws MovieNotFoundException {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + id));

        movie.setTitle(updateMovie.getTitle());
        movie.setDescription(updateMovie.getDescription());
        movie.setRating(updateMovie.getRating());

        return repository.save(movie);
    }
    
    @Override
    public ResponseEntity<String> deleteMovie(int id) throws MovieNotFoundException {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + id));

        repository.delete(movie);
        String jsonResponse = getSuccessResponseJson("Movie deleted successfully");

        return ResponseEntity.ok(jsonResponse);
    }
    
    private String getSuccessResponseJson(String message) {
        try {
            ErrorResponse response = new ErrorResponse(message);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
        	 // Handle the exception if JSON conversion fails
            ErrorResponse errorResponse = new ErrorResponse("Error converting response to JSON");
            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            errorResponse.setTimeStamp(LocalDateTime.now());
            return errorResponse.getMessage();
        }
    }

    @Override
    public Optional<Movie> findByTitle(String title) {
        return repository.findByTitle(title);
    }

    
}


	

