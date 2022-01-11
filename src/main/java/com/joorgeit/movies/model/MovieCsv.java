package com.joorgeit.movies.model;

import java.util.List;

public class MovieCsv {
	private Long year;
	private String title;
	private List<String> studios;
	private List<String> producers;
	private Boolean winner;

	public MovieCsv() {
	}

	public MovieCsv(Long year, String title, List<String> studios, List<String> producers, Boolean winner) {
		this.year = year;
		this.title = title;
		this.studios = studios;
		this.producers = producers;
		this.winner = winner;
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

	public List<String> getStudios() {
		return studios;
	}

	public void setStudios(List<String> studios) {
		this.studios = studios;
	}

	public List<String> getProducers() {
		return producers;
	}

	public void setProducers(List<String> producers) {
		this.producers = producers;
	}

	public Boolean getWinner() {
		return winner;
	}

	public void setWinner(Boolean winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Movie [year=" + year + ", title=" + title + ", studios=" + studios + ", producers=" + producers
				+ ", winner=" + winner + "]";
	}
}
