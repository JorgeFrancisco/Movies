package com.joorgeit.movies.controller;

import java.util.List;
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

import com.joorgeit.movies.model.dbapi.Movie;
import com.joorgeit.movies.service.MoviesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/movies")
@Api(value = "Endpoints for Movies.", tags = "Movies")
public class MoviesController {

	Logger logger = LoggerFactory.getLogger(MoviesController.class);

	@Autowired
	MoviesService moviesService;

	@ApiOperation(value = "Return movie list.", tags = "Movies")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 401, message = "Unauthorized. The request requires user authentication."),
			@ApiResponse(code = 403, message = "Forbidden Error."), @ApiResponse(code = 404, message = "Not found."),
			@ApiResponse(code = 405, message = "Method not allowed."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@GetMapping("/movie")
	ResponseEntity<List<Movie>> getMovies(
			@RequestParam(name = "filter") @ApiParam(value = "Filter (winner = Only winners, all = All)", required = false) Optional<String> filter,
			@RequestParam(name = "title") @ApiParam(value = "Movie title", required = false) Optional<String> title,
			@RequestParam(name = "year") @ApiParam(value = "Movie year", required = false) Optional<Long> year)
			throws Exception {
		try {
			List<Movie> response = moviesService.getMovies(filter, title, year);

			if (response == null || response.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("An error ocurred: " + e.getLocalizedMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@ApiOperation(value = "Return movie by id.", tags = "Movies")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 401, message = "Unauthorized. The request requires user authentication."),
			@ApiResponse(code = 403, message = "Forbidden Error."), @ApiResponse(code = 404, message = "Not found."),
			@ApiResponse(code = 405, message = "Method not allowed."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@GetMapping("/movie/{movieId}")
	ResponseEntity<Optional<Movie>> getMoviesById(
			@PathVariable(value = "movieId") @NotNull @DecimalMin("1") Long movieId) throws Exception {
		try {
			Optional<Movie> response = moviesService.getMoviesById(movieId);

			if (response.isPresent() == false) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("An error ocurred: " + e.getLocalizedMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@ApiOperation(value = "Delete movie by id.", tags = "Movies")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 401, message = "Unauthorized. The request requires user authentication."),
			@ApiResponse(code = 403, message = "Forbidden Error."), @ApiResponse(code = 404, message = "Not found."),
			@ApiResponse(code = 405, message = "Method not allowed."),
			@ApiResponse(code = 500, message = "Internal server error.") })
	@DeleteMapping("/{movieId}")
	public ResponseEntity<Object> delete(@PathVariable(value = "movieId") Long movieId) {
		Optional<Movie> movie = moviesService.getMoviesById(movieId);

		try {
			if (movie.isPresent()) {
				moviesService.deleteMovie(movie.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			return ResponseEntity.ok("Movie " + movie.get().getTitle() + " deleted!");
		} catch (Exception e) {
			logger.error("An error ocurred: " + e.getLocalizedMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
