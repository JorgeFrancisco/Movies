package com.joorgeit.movies.repository.dbapi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joorgeit.movies.model.ProducerWinner;
import com.joorgeit.movies.model.dbapi.MovieProducer;

public interface MovieProducerRepository extends JpaRepository<MovieProducer, Long> {

	Optional<MovieProducer> findByIdProducerIdAndMovieId(Long producerId, Long moveId);

	List<MovieProducer> findAll();

	static final String queryFindProducersWinner = "SELECT "
			+ "producerwinid, producerwinname, ABS(followingWin - previousWin) intervalwin, previouswin, followingwin "
			+ "FROM " + "(SELECT p.producer_id producerwinid, p.name producerwinname, m.year previouswin, "
			+ "LEAD(m.year, 1, m.year) OVER w AS followingwin " + "FROM movie_producer mp "
			+ "INNER JOIN producer p ON p.producer_id = mp.producer_id "
			+ "INNER JOIN  movie m ON m.movie_id = mp.movie_id " + "WHERE " + "m.winner IS NOT NULL "
			+ "AND m.winner = TRUE " + "AND m.year IS NOT NULL " + "AND m.year > 0 " + "AND m.title IS NOT NULL "
			+ "AND p.name IS NOT NULL " + "AND (:producerName IS NULL OR :producerName = '' OR p.name = :producerName) "
			+ "WINDOW w AS (PARTITION BY mp.producer_id ORDER BY m.year ASC) ) temp " + "WHERE "
			+ "(ABS(followingWin - previousWin) > 0 OR :withConsecutiveWins IS NULL OR :withConsecutiveWins = FALSE)";

	@Query(value = queryFindProducersWinner, nativeQuery = true)
	public List<ProducerWinner> findProducersWinner(@Param("producerName") String producerName,
			@Param("withConsecutiveWins") Boolean withConsecutiveWins);
}
