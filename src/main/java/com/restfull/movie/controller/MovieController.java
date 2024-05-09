package com.restfull.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfull.movie.dto.GenericResponseDTO;
import com.restfull.movie.dto.MovieRequestDTO;
import com.restfull.movie.dto.MovieResponseDTO;
import com.restfull.movie.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/movies")
@Validated
public class MovieController {

    @Autowired
    private MovieService movieService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<?> getAllMovies() {
        List<MovieResponseDTO> movies = movieService.getAllMovies();
        GenericResponseDTO responseDTO = new GenericResponseDTO().successResponse("Movies retrieved successfully");
        try {
            String moviesJson = objectMapper.writeValueAsString(movies);
            responseDTO.setMessage(moviesJson);
            return ResponseEntity.ok(responseDTO);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("An error occurred while processing movies"));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable @NotNull Integer id) {
        try {
            MovieResponseDTO movie = movieService.getMovieById(id);
            String movieJson = objectMapper.writeValueAsString(movie);
            GenericResponseDTO responseDTO = new GenericResponseDTO().successResponse("Movie retrieved successfully");
            responseDTO.setMessage(movieJson);
            return ResponseEntity.ok(responseDTO);
        } catch (NoSuchElementException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO().errorResponse("Movie not found"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        try {
            MovieResponseDTO savedMovie = movieService.saveOrUpdateMovie(movieRequestDTO);
            String savedMovieJson = objectMapper.writeValueAsString(savedMovie);
            GenericResponseDTO responseDTO = new GenericResponseDTO().successResponse("Movie created successfully");
            responseDTO.setMessage(savedMovieJson);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("An error occurred while processing the saved movie"));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable @NotNull Integer id, @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        try {
            MovieResponseDTO updatedMovie = movieService.saveOrUpdateMovie(id, movieRequestDTO);
            String updatedMovieJson = objectMapper.writeValueAsString(updatedMovie);
            GenericResponseDTO responseDTO = new GenericResponseDTO().successResponse("Movie updated successfully");
            responseDTO.setMessage(updatedMovieJson);
            return ResponseEntity.ok(responseDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO().errorResponse("Movie not found"));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("An error occurred while processing the updated movie"));
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("An error occurred while parsing date in the updated movie"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable @NotNull Integer id) {
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO().errorResponse("Movie not found"));
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponseDTO().errorResponse("An error occurred: " + ex.getMessage()));
    }
}

