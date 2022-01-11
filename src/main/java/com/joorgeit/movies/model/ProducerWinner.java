package com.joorgeit.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joorgeit.movies.model.impl.ProducerWinnerImpl;

@JsonPropertyOrder({ "producer", "interval", "previousWin", "followingWin" })
@JsonDeserialize(as = ProducerWinnerImpl.class)
public interface ProducerWinner {

	@JsonIgnore
	Long getProducerWinId();

	@JsonProperty("producer")
	String getProducerWinName();

	@JsonProperty("interval")
	Long getIntervalWin();

	@JsonProperty("previousWin")
	Long getPreviousWin();

	@JsonProperty("followingWin")
	Long getFollowingWin();
}
