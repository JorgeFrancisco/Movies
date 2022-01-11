package com.joorgeit.movies.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joorgeit.movies.model.ProducerWinner;
import com.joorgeit.movies.model.response.ProducerWinnerByMinMaxIntervalYearResponse;
import com.joorgeit.movies.service.MoviesService;
import com.joorgeit.movies.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProducersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MoviesService moviesService;

	private static final ObjectMapper mapper = new ObjectMapper()
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());

	@Test
	public void testProducerWinnerMinMax() throws Exception {
		LinkedMultiValueMap<String, String> requestProducersWinnerMinMaxParams = new LinkedMultiValueMap<>();

		requestProducersWinnerMinMaxParams.add("filter", "winnerminmax");

		MvcResult resultProducersWinnerMinMax = mockMvc.perform(MockMvcRequestBuilders.get("/producers/producer")
				.params(requestProducersWinnerMinMaxParams).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		ProducerWinnerByMinMaxIntervalYearResponse producerWinnerByMinMaxIntervalYearResponse = mapper.readValue(
				resultProducersWinnerMinMax.getResponse().getContentAsString(),
				ProducerWinnerByMinMaxIntervalYearResponse.class);

		List<ProducerWinner> mins = producerWinnerByMinMaxIntervalYearResponse.getMin();
		List<ProducerWinner> maxs = producerWinnerByMinMaxIntervalYearResponse.getMax();

		for (ProducerWinner min : mins) {
			Assertions.assertFalse(Util.isInvalidText(min.getProducerWinName()));

			Assertions.assertNotNull(min.getIntervalWin());

			Assertions.assertNotNull(min.getPreviousWin());

			Assertions.assertNotNull(min.getFollowingWin());

			@SuppressWarnings("unchecked")
			List<ProducerWinner> producerWinner = (List<ProducerWinner>) moviesService
					.getProducers(Optional.of("winner"), Optional.of(min.getProducerWinName()));

			Assertions.assertTrue(producerWinner.get(0).getProducerWinName().equals(min.getProducerWinName()));
		}

		for (ProducerWinner max : maxs) {
			Assertions.assertFalse(Util.isInvalidText(max.getProducerWinName()));

			Assertions.assertNotNull(max.getIntervalWin());

			Assertions.assertNotNull(max.getPreviousWin());

			Assertions.assertNotNull(max.getFollowingWin());

			@SuppressWarnings("unchecked")
			List<ProducerWinner> producerWinner = (List<ProducerWinner>) moviesService
					.getProducers(Optional.of("winner"), Optional.of(max.getProducerWinName()));

			Assertions.assertTrue(producerWinner.get(0).getProducerWinName().equals(max.getProducerWinName()));
		}
	}
}
