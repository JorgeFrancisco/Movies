package com.joorgeit.movies.model.dbapi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table(name = "producer")
public class Producer implements Serializable {

	private static final long serialVersionUID = 5589958912277332201L;

	@Id
	@Column(name = "producer_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producer_sequence")
	@SequenceGenerator(name = "producer_sequence", sequenceName = "producer_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "producer", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<MovieProducer> producers = new HashSet<>();

	public Producer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MovieProducer> getProducers() {
		return producers;
	}

	public void setProducers(Set<MovieProducer> producers) {
		this.producers = producers;
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
		Producer other = (Producer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producer [id=" + id + ", name=" + name + ", producers=" + producers + "]";
	}
}
