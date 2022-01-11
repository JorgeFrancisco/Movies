package com.joorgeit.movies.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.joorgeit.movies.model.MovieCsv;
import com.joorgeit.movies.model.dbapi.Movie;
import com.joorgeit.movies.model.dbapi.MovieProducer;
import com.joorgeit.movies.model.dbapi.MovieProducerKey;
import com.joorgeit.movies.model.dbapi.Producer;
import com.joorgeit.movies.repository.dbapi.MovieProducerRepository;
import com.joorgeit.movies.repository.dbapi.MovieRepository;
import com.joorgeit.movies.repository.dbapi.ProducerRepository;
import com.joorgeit.movies.util.Util;

@Service
public class LoaderMoviesService {

	Logger logger = LoggerFactory.getLogger(LoaderMoviesService.class);

	@Autowired
	@Qualifier("resourceMovies")
	private List<MovieCsv> resourceMovies;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ProducerRepository producerRepository;

	@Autowired
	MovieProducerRepository movieProducerRepository;

	@PostConstruct
	public void init() throws Exception {
		loadMovies();
	}

	public void loadMovies() {
		logger.info("Quantity of movies loaded from csv file = "
				+ (resourceMovies == null || resourceMovies.isEmpty() ? 0 : resourceMovies.size()));

		if (resourceMovies != null && resourceMovies.isEmpty() == false) {
			for (MovieCsv movieCsv : resourceMovies) {
				List<String> producersCsv = movieCsv.getProducers();

				List<Producer> producers = new ArrayList<Producer>();

				for (String producerCsv : producersCsv) {
					String name = producerCsv.trim();

					if (Util.isInvalidText(name) == false) {
						Producer producer = producerRepository.findFirstByName(name);

						if (producer == null) {
							producer = new Producer();
							producer.setName(name);

							producerRepository.save(producer);
						}

						producers.add(producer);
					}
				}

				if (Util.isInvalidText(movieCsv.getTitle()) == false) {
					String title = movieCsv.getTitle().trim();

					if (Util.isInvalidText(title) == false) {
						Movie movie = movieRepository.findFirstByTitle(title);

						if (movie == null) {
							movie = new Movie();
							movie.setYear(movieCsv.getYear());
							movie.setTitle(title);
							movie.setWinner(movieCsv.getWinner());

							movieRepository.save(movie);
						}

						if (producers != null && producers.isEmpty() == false) {
							for (Producer producer : producers) {
								Optional<MovieProducer> movieProducerOptional = movieProducerRepository
										.findByIdProducerIdAndMovieId(producer.getId(), movie.getId());

								if (movieProducerOptional.isPresent() == false) {
									MovieProducer movieProducer = new MovieProducer();

									movieProducer.setId(new MovieProducerKey());
									movieProducer.setMovie(movie);
									movieProducer.setProducer(producer);

									movieProducerRepository.save(movieProducer);
								}
							}
						}
					}
				}
			}
		}
	}
}
