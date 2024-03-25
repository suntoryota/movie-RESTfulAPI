package com.soya.movierestful.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soya.movierestful.dto.MovieDto;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.exception.MovieNotFoundException;
import com.soya.movierestful.repository.MovieRepository;
import com.soya.movierestful.service.impl.MovieServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplUnitTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void testCreateMovie() {
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Test Movie");
        movieDto.setDescription("Test Description");
        movieDto.setRating(4.2f);
        movieDto.setImage("test.jpg");

        when(movieRepository.existsByTitle(movieDto.getTitle())).thenReturn(false);
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> {
            Movie movie = invocation.getArgument(0);
            movie.setId(1); // Set a dummy ID for the new movie
            return movie;
        });

        Movie createdMovie = movieService.createMovie(movieDto);

        assertNotNull(createdMovie);
        assertEquals(movieDto.getTitle(), createdMovie.getTitle());
        assertEquals(movieDto.getDescription(), createdMovie.getDescription());
        assertEquals(movieDto.getRating(), createdMovie.getRating());
        assertEquals(movieDto.getImage(), createdMovie.getImage());
    }

    @Test
    public void testUpdateMovie() throws MovieNotFoundException {
        int movieId = 1;
        MovieDto updateMovieDto = new MovieDto();
        updateMovieDto.setTitle("Updated Movie");
        updateMovieDto.setDescription("Updated Description");
        updateMovieDto.setRating(4.8f);
        updateMovieDto.setImage("updated.jpg");

        Movie existingMovie = new Movie();
        existingMovie.setTitle("Existing Movie");
        existingMovie.setDescription("Existing Description");
        existingMovie.setRating(4.5f);
        existingMovie.setImage("existing.jpg");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class))).thenAnswer(invocation -> {
            Movie movie = invocation.getArgument(0);
            movie.setId(movieId); // Set the ID of the updated movie
            return movie;
        });

        Movie updatedMovie = movieService.updateMovie(movieId, updateMovieDto);

        assertNotNull(updatedMovie);
        assertEquals(movieId, updatedMovie.getId()); // Assert the ID of the updated movie
        assertEquals(updateMovieDto.getTitle(), updatedMovie.getTitle());
        assertEquals(updateMovieDto.getDescription(), updatedMovie.getDescription());
        assertEquals(updateMovieDto.getRating(), updatedMovie.getRating());
        assertEquals(existingMovie.getImage(), updatedMovie.getImage());
    }

    // Add more test cases for other service methods
}
