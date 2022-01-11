package com.joorgeit.movies.model.dbapi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MovieProducerKey implements Serializable {

	private static final long serialVersionUID = 7029958433613557553L;

	@Column(name = "movie_id")
	private Long movieId;

	@Column(name = "producer_id")
	private Long producerId;

	public MovieProducerKey() {
	}

	public MovieProducerKey(Long movieId, Long producerId) {
		this.movieId = movieId;
		this.producerId = producerId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getProducerId() {
		return producerId;
	}

	public void setProducerId(Long producerId) {
		this.producerId = producerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
		result = prime * result + ((producerId == null) ? 0 : producerId.hashCode());
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
		MovieProducerKey other = (MovieProducerKey) obj;
		if (movieId == null) {
			if (other.movieId != null)
				return false;
		} else if (!movieId.equals(other.movieId))
			return false;
		if (producerId == null) {
			if (other.producerId != null)
				return false;
		} else if (!producerId.equals(other.producerId))
			return false;
		return true;
	}
}
