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
import com.joorgeit.movies.model.response.ProducerWinnerByMinMaxIntervalWinResponse;
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
				return getProducersWinnerByMinMaxIntervalWin();
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

	public ProducerWinnerByMinMaxIntervalWinResponse getProducersWinnerByMinMaxIntervalWin() {
		List<ProducerWinner> producersWinner = movieProducerRepository.findProducersWinner("", true);

		if (producersWinner != null && producersWinner.isEmpty() == false) {
			Optional<ProducerWinner> producerByMinIntervalWin = producersWinner.stream()
					.min(Comparator.comparing(ProducerWinner::getIntervalWin));

			Optional<ProducerWinner> producerByMaxIntervalWin = producersWinner.stream()
					.max(Comparator.comparing(ProducerWinner::getIntervalWin));

			if (producerByMinIntervalWin.isPresent() && producerByMaxIntervalWin.isPresent()) {
				final Long minIntervalWin = producerByMinIntervalWin.get().getIntervalWin();
				final Long maxIntervalWin = producerByMaxIntervalWin.get().getIntervalWin();

				List<ProducerWinner> producersByMinIntervalWin = producersWinner.stream().filter(Objects::nonNull)
						.filter(p -> p.getIntervalWin() == minIntervalWin).collect(Collectors.toList());

				List<ProducerWinner> producersByMaxIntervalWin = producersWinner.stream().filter(Objects::nonNull)
						.filter(p -> p.getIntervalWin() == maxIntervalWin).collect(Collectors.toList());

				return new ProducerWinnerByMinMaxIntervalWinResponse(producersByMinIntervalWin,
						producersByMaxIntervalWin);
			}
		}

		return null;
	}
}
