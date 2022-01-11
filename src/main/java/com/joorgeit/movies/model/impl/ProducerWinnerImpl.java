package com.joorgeit.movies.model.impl;

import com.joorgeit.movies.model.ProducerWinner;

public class ProducerWinnerImpl implements ProducerWinner {

	private Long producerWinId;

	private String producerWinName;

	private Long intervalWin;

	private Long previousWin;

	private Long followingWin;

	public ProducerWinnerImpl() {
	}

	public ProducerWinnerImpl(Long producerWinId, String producerWinName, Long intervalWin, Long previousWin,
			Long followingWin) {
		this.producerWinId = producerWinId;
		this.producerWinName = producerWinName;
		this.intervalWin = intervalWin;
		this.previousWin = previousWin;
		this.followingWin = followingWin;
	}

	@Override
	public Long getProducerWinId() {
		return producerWinId;
	}

	@Override
	public String getProducerWinName() {
		return producerWinName;
	}

	@Override
	public Long getIntervalWin() {
		return intervalWin;
	}

	@Override
	public Long getPreviousWin() {
		return previousWin;
	}

	@Override
	public Long getFollowingWin() {
		return followingWin;
	}

	public void setProducerWinId(Long producerWinId) {
		this.producerWinId = producerWinId;
	}

	public void setProducerWinName(String producerWinName) {
		this.producerWinName = producerWinName;
	}

	public void setIntervalWin(Long intervalWin) {
		this.intervalWin = intervalWin;
	}

	public void setPreviousWin(Long previousWin) {
		this.previousWin = previousWin;
	}

	public void setFollowingWin(Long followingWin) {
		this.followingWin = followingWin;
	}
}
