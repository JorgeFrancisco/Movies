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
	Long getProducerId();

	@JsonProperty("producer")
	String getProducerName();

	@JsonProperty("interval")
	Long getIntervalYears();

	@JsonProperty("previousWin")
	Long getFirstYear();

	@JsonProperty("followingWin")
	Long getLastYear();
}
