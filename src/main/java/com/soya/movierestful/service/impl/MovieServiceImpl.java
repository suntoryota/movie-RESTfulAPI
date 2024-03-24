package com.soya.movierestful.service.impl;

import java.util.List;

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
    public Movie detailsMovie(int id)  throws MovieNotFoundException{   
		return repository.findById(id).orElseThrow(() 
				-> new MovieNotFoundException("Movie not found with id: " + id));
   }
	
    @Override
    public Movie updateMovie(int id, MovieDto updateMovie) throws MovieNotFoundException {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));

        movie.setTitle(updateMovie.getTitle());
        movie.setDescription(updateMovie.getDescription());
        movie.setRating(updateMovie.getRating());

        return repository.save(movie);
    }
    
    @Override
    public ResponseEntity<String> deleteMovie(int id) throws MovieNotFoundException {
        Movie movie = repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));

            repository.delete(movie);
            ErrorResponse response = new ErrorResponse("Movie deleted successfully");
            String jsonResponse;

            try {
                // Convert the response object to JSON
                ObjectMapper mapper = new ObjectMapper();
                jsonResponse = mapper.writeValueAsString(response);
            } catch (JsonProcessingException e) {
                // Handle the exception if JSON conversion fails
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error converting response to JSON");
            }

            return ResponseEntity.ok(jsonResponse);
        }
     }
    



	

