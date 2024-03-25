package com.soya.movierestful.test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soya.movierestful.MovierestfulApplication;
import com.soya.movierestful.entity.Movie;
import com.soya.movierestful.service.MovieService;


@SpringBootTest(classes = MovierestfulApplication.class)
@AutoConfigureMockMvc
public class MovieControllerUnitTest {
   
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setDescription("Description 1");
        movie1.setRating(4.5f);
        movie1.setImage("image1.jpg");

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setDescription("Description 2");
        movie2.setRating(3.7f);
        movie2.setImage("image2.jpg");

        movies.add(movie1);
        movies.add(movie2);

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/api/movie/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movies)));
    }

   
}

