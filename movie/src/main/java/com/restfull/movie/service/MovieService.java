package com.restfull.movie.service;

import com.restfull.movie.dto.MovieRequestDTO;
import com.restfull.movie.dto.MovieResponseDTO;
import com.restfull.movie.model.MovieModel;
import com.restfull.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<MovieResponseDTO> getAllMovies() {
        List<MovieModel> movies = movieRepository.findAll();
        return movies.stream().map(this::convertToMovieResponseDTO).collect(Collectors.toList());
    }

    public MovieResponseDTO getMovieById(Integer id) {
        MovieModel movie = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie not found with id: " + id));
        return convertToMovieResponseDTO(movie);
    }

    public MovieResponseDTO saveOrUpdateMovie(MovieRequestDTO movieRequestDTO) throws ParseException {
        MovieModel movie = convertToMovie(movieRequestDTO);
        MovieModel savedMovie = movieRepository.save(movie);
        return convertToMovieResponseDTO(savedMovie);
    }

    public MovieResponseDTO saveOrUpdateMovie(Integer id, MovieRequestDTO movieRequestDTO) throws ParseException {
        MovieModel movieToUpdate = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie not found with id: " + id));
        movieToUpdate.setTitle(movieRequestDTO.getTitle());
        movieToUpdate.setDescription(movieRequestDTO.getDescription());
        movieToUpdate.setRating(movieRequestDTO.getRating());
        movieToUpdate.setImage(movieRequestDTO.getImage());
        movieToUpdate.setCreatedAt(movieToUpdate.getCreatedAt());
        movieToUpdate.setUpdatedAt(new Date());
        MovieModel updatedMovie = movieRepository.save(movieToUpdate);
        return convertToMovieResponseDTO(updatedMovie);
    }

    public void deleteMovie(Integer id) {
        if (!movieRepository.existsById(id)) {
            throw new NoSuchElementException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

    private MovieResponseDTO convertToMovieResponseDTO(MovieModel movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setRating(movie.getRating());
        dto.setImage(movie.getImage());
        dto.setCreatedAt(sdf.format(movie.getCreatedAt()));
        dto.setUpdatedAt(sdf.format(movie.getUpdatedAt()));
        return dto;
    }

    private MovieModel convertToMovie(MovieRequestDTO movieRequestDTO) throws ParseException {
        MovieModel movie = new MovieModel();
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setRating(movieRequestDTO.getRating());
        movie.setImage(movieRequestDTO.getImage());
        movie.setCreatedAt(sdf.parse(movieRequestDTO.getCreated_at()));
        movie.setUpdatedAt(sdf.parse(movieRequestDTO.getUpdated_at()));
        return movie;
    }
}


