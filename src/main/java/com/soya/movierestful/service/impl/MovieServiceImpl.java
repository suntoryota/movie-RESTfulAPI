package com.soya.movierestful.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public Movie createMovie(MovieDto movieDto) {
		
        if (repository.existsByTitle(movieDto.getTitle())) { 
            throw new MovieAlreadyExistsException("Movie with title '" + movieDto.getTitle() + "' already exists!");
        }
        
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(formatter);

        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setRating(movieDto.getRating());
        movie.setImage(movieDto.getImage());
        movie.setCreated_at(formattedDateTime);

		return repository.save(movie);
	}
	
	@Override
	public List<Movie> getAllMovies(){
		return repository.findAll();
	}
	
    @Override
    public Movie getMovieById(int id) throws MovieNotFoundException {
        Movie movie = repository.findById(id);
        		
        if (movie != null) {
        	return repository.findById(id);
    } else {
    	throw new MovieNotFoundException("Movie not found with id: " + id);
    	}
   }
	
	
    @Override
    public Movie updateMovie(int id, MovieDto updateMovie) throws MovieNotFoundException {
        Movie movie = repository.findById(id);
         
        if (movie != null) {
        	 movie.setTitle(updateMovie.getTitle());
             movie.setDescription(updateMovie.getDescription());
             movie.setRating(updateMovie.getRating());
             movie.setUpdated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

             return repository.save(movie);
		}else {
			throw new MovieNotFoundException("Movie not found with id: " + id);
		}
       
    }
	
    
    @Override
    public ResponseEntity<String> deleteMovie(int id) throws MovieNotFoundException {
        Movie movie = repository.findById(id);
        if (movie == null) {
            throw new MovieNotFoundException("Movie not found with id: " + id);
        } else {
            repository.delete(movie);
            return new ResponseEntity<>("Movie deleted successfully", HttpStatus.OK);
        }
    }



	
}
