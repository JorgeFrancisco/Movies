package com.joorgeit.movies.model.dbapi;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "movie_producer")
public class MovieProducer implements Serializable {

	private static final long serialVersionUID = 253672276983268542L;

	@EmbeddedId
	private MovieProducerKey id;

	@ManyToOne
	@MapsId("movieId")
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne
	@MapsId("producerId")
	@JoinColumn(name = "producer_id")
	private Producer producer;

	public MovieProducer() {
	}

	public MovieProducerKey getId() {
		return id;
	}

	public void setId(MovieProducerKey id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieProducer other = (MovieProducer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
