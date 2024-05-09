package com.restfull.movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfull.movie.controller.MovieController;
import com.restfull.movie.dto.GenericResponseDTO;
import com.restfull.movie.dto.MovieRequestDTO;
import com.restfull.movie.dto.MovieResponseDTO;
import com.restfull.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testCreateMovies() throws Exception {

        // Mock data
        MovieRequestDTO movieRequestDTO = new MovieRequestDTO();
        movieRequestDTO.setTitle("Pengabdi Setan 2 Comunion");
        movieRequestDTO.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movieRequestDTO.setRating(7.0f);
        movieRequestDTO.setCreated_at("2022-08-01 10:56:31");
        movieRequestDTO.setUpdated_at("2022-08-13 09:30:23");

        // Mock service response
        MovieResponseDTO mockResponse = new MovieResponseDTO();
        mockResponse.setTitle("Pengabdi Setan 2 Comunion");
        mockResponse.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        mockResponse.setRating(7.0f);
        mockResponse.setCreatedAt("2022-08-01 10:56:31");
        mockResponse.setUpdatedAt("2022-08-13 09:30:23");

        // Perform POST request
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(movieRequestDTO)))
                .andExpect(status().isCreated());
    }


    @Test
    public void testUpdateMovie() throws Exception {
        // Mock data
        MovieRequestDTO movieRequestDTO = new MovieRequestDTO();
        movieRequestDTO.setTitle("BARU Pengabdi Setan 2 Comunion");
        movieRequestDTO.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        movieRequestDTO.setRating(7.0f);
        movieRequestDTO.setCreated_at("2022-08-01 10:56:31");
        movieRequestDTO.setUpdated_at("2022-08-13 09:30:23");

        // Mock service response
        MovieResponseDTO mockResponse = new MovieResponseDTO();
        mockResponse.setTitle("BARU Pengabdi Setan 2 Comunion");
        mockResponse.setDescription("dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.");
        mockResponse.setRating(7.0f);
        mockResponse.setCreatedAt("2022-08-01 10:56:31");
        mockResponse.setUpdatedAt("2022-08-13 09:30:23");

        // Perform POST request
        mockMvc.perform(patch("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(movieRequestDTO)))
                .andExpect(status().isOk());
    }


    @Test
    public void testGetMovieById() throws JsonProcessingException {
        int movieId = 1;

        MovieResponseDTO movie = new MovieResponseDTO();
        movie.setId(movieId);

        when(movieService.getMovieById(eq(movieId))).thenReturn(movie);

        ResponseEntity<?> responseEntity = movieController.getMovieById(movieId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteMovie() {
        int movieId = 1;

        ResponseEntity<?> responseEntity = movieController.deleteMovie(movieId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(movieService, times(1)).deleteMovie(eq(movieId));
    }
}


