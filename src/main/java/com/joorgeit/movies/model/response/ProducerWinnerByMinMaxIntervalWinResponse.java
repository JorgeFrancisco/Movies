package com.joorgeit.movies.model.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joorgeit.movies.model.ProducerWinner;

public class ProducerWinnerByMinMaxIntervalWinResponse implements Serializable {

	private static final long serialVersionUID = -4334240554186265884L;

	@JsonProperty("min")
	private List<ProducerWinner> min;

	@JsonProperty("max")
	private List<ProducerWinner> max;

	public ProducerWinnerByMinMaxIntervalWinResponse() {
	}

	public ProducerWinnerByMinMaxIntervalWinResponse(List<ProducerWinner> min, List<ProducerWinner> max) {
		this.min = min;
		this.max = max;
	}

	public List<ProducerWinner> getMin() {
		return min;
	}

	public void setMin(List<ProducerWinner> min) {
		this.min = min;
	}

	public List<ProducerWinner> getMax() {
		return max;
	}

	public void setMax(List<ProducerWinner> max) {
		this.max = max;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((min == null) ? 0 : min.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "ProducerWinnerByMinMaxIntervalWinResponse [min=" + min + ", max=" + max + "]";
	}
}
