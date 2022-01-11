package com.joorgeit.movies.repository.dbapi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.joorgeit.movies.model.dbapi.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
	long count();

	Optional<Producer> findById(Long id);

	List<Producer> findAll();

	List<Producer> findByName(@Param("name") String name);

	Producer findFirstByName(@Param("name") String name);
}
