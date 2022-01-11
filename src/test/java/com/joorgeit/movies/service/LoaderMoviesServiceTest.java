package com.joorgeit.movies.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.joorgeit.movies.model.dbapi.Movie;
import com.joorgeit.movies.model.dbapi.Producer;
import com.joorgeit.movies.repository.dbapi.MovieRepository;
import com.joorgeit.movies.repository.dbapi.ProducerRepository;

@SpringBootTest
@ActiveProfiles("test")
public class LoaderMoviesServiceTest {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ProducerRepository producerRepository;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testLoadMovies() {
		List<Movie> movies = movieRepository.findAll();

		for (Movie movie : movies) {
			assertNotNull(movie.getTitle());
			assertNotNull(movie.getYear());
			assertNotNull(movie.getWinner());
		}
	}

	@Test
	public void testLoadProducers() {
		List<Producer> producers = producerRepository.findAll();

		for (Producer producer : producers) {
			assertNotNull(producer.getName());
		}
	}
}
