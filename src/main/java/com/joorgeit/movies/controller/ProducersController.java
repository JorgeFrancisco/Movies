package com.joorgeit.movies.controller;

import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joorgeit.movies.model.dbapi.Producer;
import com.joorgeit.movies.model.response.ProducerWinnerByMinMaxIntervalYearResponse;
import com.joorgeit.movies.service.MoviesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/producers")
@Api(value = "Endpoints for Producers.", tags = "Producers")
public class ProducersController {

	Logger logger = LoggerFactory.getLogger(ProducersController.class);

	@Autowired
	MoviesService moviesService;

	@ApiOperation(value = "Return producer list.", tags = "Producers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 401, message = "Unauthorized. The request requires user authentication."),
			@ApiResponse(code = 403, message = "Forbidden Error."), @ApiResponse(code = 404, message = "Not found."),
			@ApiResponse(code = 405, message = "Method not allowed."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@GetMapping("/producer")
	ResponseEntity<Object> getProducers(
			@RequestParam(name = "filter") @ApiParam(value = "Filter (winner = Only winners, winnerminmax = winner min/max by interval year, all = All)", required = false) Optional<String> filter,
			@RequestParam(name = "name") @ApiParam(value = "Producer name", required = false) Optional<String> name) {
		try {
			Object response = moviesService.getProducers(filter, name);

			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			if (response instanceof ProducerWinnerByMinMaxIntervalYearResponse) {
				ProducerWinnerByMinMaxIntervalYearResponse producerWinnerByMinMaxIntervalYearResponse = (ProducerWinnerByMinMaxIntervalYearResponse) response;

				if (producerWinnerByMinMaxIntervalYearResponse == null
						|| producerWinnerByMinMaxIntervalYearResponse.getMin() == null
						|| producerWinnerByMinMaxIntervalYearResponse.getMin().isEmpty()
						|| producerWinnerByMinMaxIntervalYearResponse.getMax() == null
						|| producerWinnerByMinMaxIntervalYearResponse.getMax().isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("An error ocurred: " + e.getLocalizedMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@ApiOperation(value = "Return producer by id.", tags = "Producers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 401, message = "Unauthorized. The request requires user authentication."),
			@ApiResponse(code = 403, message = "Forbidden Error."), @ApiResponse(code = 404, message = "Not found."),
			@ApiResponse(code = 405, message = "Method not allowed."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@GetMapping("/producer/{producerId}")
	ResponseEntity<Optional<Producer>> getProducersById(
			@PathVariable(value = "producerId") @NotNull @DecimalMin("1") Long producerId) {
		try {
			Optional<Producer> response = moviesService.getProducersById(producerId);

			if (response.isPresent() == false) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("An error ocurred: " + e.getLocalizedMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@ApiOperation(value = "Delete producer by id.", tags = "Producers")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 401, message = "Unauthorized. The request requires user authentication."),
			@ApiResponse(code = 403, message = "Forbidden Error."), @ApiResponse(code = 404, message = "Not found."),
			@ApiResponse(code = 405, message = "Method not allowed."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@DeleteMapping("/{producerId}")
	public ResponseEntity<Object> delete(
			@PathVariable(value = "producerId") @NotNull @DecimalMin("1") Long producerId) {
		Optional<Producer> producer = moviesService.getProducersById(producerId);

		try {
			if (producer.isPresent()) {
				moviesService.deleteProducer(producer.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.ok("Producer " + producer.get().getName() + " deleted!");
		} catch (Exception e) {
			logger.error("An error ocurred: " + e.getLocalizedMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
