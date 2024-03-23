package com.soya.movierestful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soya.movierestful.controller.MovieController;
import com.soya.movierestful.dto.MovieDto;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.exception.MovieAlreadyExistsException;
import com.soya.movierestful.exception.MovieNotFoundException;
import com.soya.movierestful.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovierestfulApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    private MovieDto movieDto;
    private Movie movie;
    private List<Movie> movieList;

    @BeforeEach
    public void setUp() {
        movieDto = new MovieDto("Test Movie", "Test Description", 4.5f, "test.jpg", LocalDateTime.now(), LocalDateTime.now());
        movie = new Movie(1, "Test Movie", "Test Description", 4.5f, "test.jpg", "2023-03-24 12:00:00", "2023-03-24 12:00:00");
        movieList = new ArrayList<>();
        movieList.add(movie);
    }

    @Test
    public void testGetAllMovies() throws Exception {
        when(movieService.getAllMovies()).thenReturn(movieList);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    public void testGetMovieById() throws Exception {
        when(movieService.getMovieById(1)).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Movie"));
    }

    @Test
    public void testGetMovieByIdNotFound() throws Exception {
        when(movieService.getMovieById(2)).thenThrow(new MovieNotFoundException("Movie not found with id: 2"));

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateMovie() throws Exception {
        when(movieService.createMovie(any(MovieDto.class))).thenReturn(movie);

        mockMvc.perform(MockMvcRequestBuilders.post("/movies/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Movie"));
    }

    @Test
    public void testCreateMovieAlreadyExists() throws Exception {
        doThrow(new MovieAlreadyExistsException("Movie with title 'Test Movie' already exists!"))
                .when(movieService).createMovie(any(MovieDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/movies/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

	@Test
	public void testUpdateMovie() throws Exception {
		MovieDto updatedMovieDto = new MovieDto("Updated Movie", "Updated Description", 4.0f, "updated.jpg", LocalDateTime.now(), LocalDateTime.now());
		Movie updatedMovie = new Movie(1, "Updated Movie", "Updated Description", 4.0f, "updated.jpg", "2023-03-24 12:00:00", "2023-03-24 13:00:00");
	
		when(movieService.updateMovie(1, updatedMovieDto)).thenReturn(updatedMovie);
	
		mockMvc.perform(MockMvcRequestBuilders.put("/movies/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedMovieDto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Movie"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"));
	}
	
	@Test
	public void testUpdateMovieNotFound() throws Exception {
		MovieDto updatedMovieDto = new MovieDto("Updated Movie", "Updated Description", 4.0f, "updated.jpg", LocalDateTime.now(), LocalDateTime.now());
	
		when(movieService.updateMovie(2, updatedMovieDto)).thenThrow(new MovieNotFoundException("Movie not found with id: 2"));
	
		mockMvc.perform(MockMvcRequestBuilders.put("/movies/2")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedMovieDto)))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testDeleteMovie() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/movies/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Movie deleted successfully!"));
	}
	
	@Test
	public void testDeleteMovieNotFound() throws Exception {
		doThrow(new MovieNotFoundException("Movie not found with id: 2")).when(movieService).deleteMovie(2);
	
		mockMvc.perform(MockMvcRequestBuilders.delete("/movies/2"))
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(MockMvcResultMatchers.content().string("The movie with ID : 2 not found!"));
	}
}