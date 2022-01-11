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

	static final String queryFindProducersWinner = "SELECT producerId producerid, producerName producername, "
			+ "firstYear firstyear, lastYear lastyear, intervalYears intervalyears FROM (SELECT "
			+ "DISTINCT (mp.producer_id) producerId, p.name producerName, FIRST_VALUE(year) OVER w firstYear "
			+ ",LAST_VALUE(year) OVER w lastYear "
			+ ",ABS(LAST_VALUE(year) OVER w - FIRST_VALUE(year) OVER w) intervalYears FROM movie_producer mp "
			+ "INNER JOIN producer p ON p.producer_id = mp.producer_id "
			+ "INNER JOIN  movie m ON m.movie_id = mp.movie_id " + "WHERE m.winner IS NOT NULL "
			+ "AND m.winner = TRUE " + "AND m.year IS NOT NULL " + "AND m.year > 0 " + "AND m.title IS NOT NULL "
			+ "AND p.name IS NOT NULL " + "WINDOW w AS ( "
			+ "PARTITION BY mp.producer_id RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING)) temp "
			+ "WHERE intervalYears >= :interval AND " + "(:name IS null OR :name = '' OR producerName = :name)";

	@Query(value = queryFindProducersWinner, nativeQuery = true)
	public List<ProducerWinner> findProducersWinner(@Param("name") String name, @Param("interval") Long interval);
}
