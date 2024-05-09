package com.restfull.movie.repository;

import com.restfull.movie.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, Integer> {
}
