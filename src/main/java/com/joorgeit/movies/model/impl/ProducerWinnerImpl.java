package com.joorgeit.movies.model.impl;

import com.joorgeit.movies.model.ProducerWinner;

public class ProducerWinnerImpl implements ProducerWinner {

	private Long producerId;

	private String producerName;

	private Long intervalYears;

	private Long firstYear;

	private Long lastYear;

	public ProducerWinnerImpl() {
	}

	public ProducerWinnerImpl(Long producerId, String producerName, Long intervalYears, Long firstYear, Long lastYear) {
		this.producerId = producerId;
		this.producerName = producerName;
		this.intervalYears = intervalYears;
		this.firstYear = firstYear;
		this.lastYear = lastYear;
	}

	@Override
	public Long getProducerId() {
		return producerId;
	}

	@Override
	public String getProducerName() {
		return producerName;
	}

	@Override
	public Long getIntervalYears() {
		return intervalYears;
	}

	@Override
	public Long getFirstYear() {
		return firstYear;
	}

	@Override
	public Long getLastYear() {
		return lastYear;
	}

	public void setProducerId(Long producerId) {
		this.producerId = producerId;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public void setIntervalYears(Long intervalYears) {
		this.intervalYears = intervalYears;
	}

	public void setFirstYear(Long firstYear) {
		this.firstYear = firstYear;
	}

	public void setLastYear(Long lastYear) {
		this.lastYear = lastYear;
	}
}
