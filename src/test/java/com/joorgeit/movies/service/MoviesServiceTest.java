package com.joorgeit.movies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.joorgeit.movies.model.dbapi.Movie;
import com.joorgeit.movies.model.dbapi.Producer;

@SpringBootTest
@ActiveProfiles("test")
public class MoviesServiceTest {

	@Autowired
	MoviesService moviesService;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testCountMovies() {
		List<Movie> movies = moviesService.getMovies(Optional.empty(), Optional.empty(), Optional.empty());

		assertNotNull(movies);
		assertFalse(movies.isEmpty());
		assertEquals(208, movies.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCountProducers() {
		Object producers = moviesService.getProducers(Optional.empty(), Optional.empty());

		assertNotNull(producers);
		assertFalse(((List<Producer>) producers).isEmpty());
		assertEquals(360, ((List<Producer>) producers).size());
	}

	@Test
	public void testFindMovieById() {
		Optional<Movie> firstMovie = moviesService.getMoviesById(26L);

		assertNotNull(firstMovie);
		assertEquals("Melhor dev Sênior pra contratar", firstMovie.get().getTitle());
		assertEquals(1983L, firstMovie.get().getYear());

		Optional<Movie> secondMovie = moviesService.getMoviesById(208L);

		assertNotNull(secondMovie);
		assertEquals("Melhor dev Sênior pra contratar 2", secondMovie.get().getTitle());
		assertEquals(2022L, secondMovie.get().getYear());
	}
}
