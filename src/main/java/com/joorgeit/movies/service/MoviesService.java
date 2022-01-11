package com.joorgeit.movies.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joorgeit.movies.model.ProducerWinner;
import com.joorgeit.movies.model.dbapi.Movie;
import com.joorgeit.movies.model.dbapi.Producer;
import com.joorgeit.movies.model.response.ProducerWinnerByMinMaxIntervalYearResponse;
import com.joorgeit.movies.repository.dbapi.MovieProducerRepository;
import com.joorgeit.movies.repository.dbapi.MovieRepository;
import com.joorgeit.movies.repository.dbapi.ProducerRepository;

@Service
public class MoviesService {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ProducerRepository producerRepository;

	@Autowired
	MovieProducerRepository movieProducerRepository;

	public List<Movie> getMovies(Optional<String> filter, Optional<String> title, Optional<Long> year) {
		if (year.isPresent()) {
			if (filter.isPresent()) {
				if ("winner".equals(filter.get().toLowerCase())) {
					return movieRepository.findByYearAndWinnerTrue(year.get());
				}
			}

			return movieRepository.findByYear(year.get());
		}

		if (title.isPresent()) {
			if (filter.isPresent()) {
				if ("winner".equals(filter.get().toLowerCase())) {
					return movieRepository.findByTitleAndWinnerTrue(title.get());
				}
			}

			return movieRepository.findByTitle(title.get());
		}

		if (filter.isPresent()) {
			if ("winner".equals(filter.get().toLowerCase())) {
				return movieRepository.findByWinnerTrue();
			}
		}

		return movieRepository.findAll();
	}

	public Optional<Movie> getMoviesById(Long movieId) {
		return movieRepository.findById(movieId);
	}

	public void deleteMovie(Movie movie) {
		movieRepository.delete(movie);
	}

	public Object getProducers(Optional<String> filter, Optional<String> name) {
		if (name.isPresent()) {
			if (filter.isPresent()) {
				if ("winner".equals(filter.get().toLowerCase())) {
					return movieProducerRepository.findProducersWinner(name.get(), false);
				}
			}

			return producerRepository.findByName(name.get());
		}
		if (filter.isPresent()) {
			if ("winner".equals(filter.get().toLowerCase())) {
				return movieProducerRepository.findProducersWinner("", false);
			} else if ("winnerminmax".equals(filter.get().toLowerCase())) {
				return getProducersWinnerByMinMaxIntervalYear();
			}
		}
		return producerRepository.findAll();
	}

	public Optional<Producer> getProducersById(Long producerId) {
		return producerRepository.findById(producerId);
	}

	public void deleteProducer(Producer producer) {
		producerRepository.delete(producer);
	}

	public ProducerWinnerByMinMaxIntervalYearResponse getProducersWinnerByMinMaxIntervalYear() {
		List<ProducerWinner> producersWinner = movieProducerRepository.findProducersWinner("", true);

		if (producersWinner != null && producersWinner.isEmpty() == false) {
			Optional<ProducerWinner> producerByMinIntervalYear = producersWinner.stream()
					.min(Comparator.comparing(ProducerWinner::getIntervalWin));

			Optional<ProducerWinner> producerByMaxIntervalYear = producersWinner.stream()
					.max(Comparator.comparing(ProducerWinner::getIntervalWin));

			if (producerByMinIntervalYear.isPresent() && producerByMaxIntervalYear.isPresent()) {
				final Long minIntervalYear = producerByMinIntervalYear.get().getIntervalWin();
				final Long maxIntervalYear = producerByMaxIntervalYear.get().getIntervalWin();

				List<ProducerWinner> producersByMinIntervalYear = producersWinner.stream().filter(Objects::nonNull)
						.filter(p -> p.getIntervalWin() == minIntervalYear).collect(Collectors.toList());

				List<ProducerWinner> producersByMaxIntervalYear = producersWinner.stream().filter(Objects::nonNull)
						.filter(p -> p.getIntervalWin() == maxIntervalYear).collect(Collectors.toList());

				return new ProducerWinnerByMinMaxIntervalYearResponse(producersByMinIntervalYear,
						producersByMaxIntervalYear);
			}
		}

		return null;
	}
}
