package com.joorgeit.movies.repository.dbapi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.joorgeit.movies.model.dbapi.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	long count();

	Optional<Movie> findById(Long id);

	List<Movie> findAll();

	List<Movie> findByWinnerTrue();

	List<Movie> findByYear(@Param("year") Long year);

	List<Movie> findByYearAndWinnerTrue(@Param("year") Long year);

	List<Movie> findByTitle(@Param("title") String title);

	List<Movie> findByTitleAndWinnerTrue(@Param("title") String title);

	Movie findFirstByTitle(@Param("title") String title);
}
