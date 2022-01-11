package com.joorgeit.movies.model.dbapi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {

	private static final long serialVersionUID = -8566757483184727375L;

	@Id
	@Column(name = "movie_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
	@SequenceGenerator(name = "movie_sequence", sequenceName = "movie_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "year")
	private Long year;

	@Column(name = "title")
	private String title;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
	private Set<MovieProducer> producers = new HashSet<>();

	@Column(name = "winner")
	private Boolean winner;

	public Movie() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<MovieProducer> getProducers() {
		return producers;
	}

	public void setProducers(Set<MovieProducer> producers) {
		this.producers = producers;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
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
		Movie other = (Movie) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", year=" + year + ", title=" + title + ", producers=" + producers + ", winner="
				+ winner + "]";
	}
}
