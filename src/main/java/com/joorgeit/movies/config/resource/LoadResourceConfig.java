package com.joorgeit.movies.config.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.joorgeit.movies.model.MovieCsv;

@Configuration
public class LoadResourceConfig {

	@Value("${movielist.filename}")
	private String movieListFileName;

	@Bean
	public List<MovieCsv> resourceMovies() {
		ResourceReader resourceReader = new ResourceReader();

		return resourceReader.loadMovies(movieListFileName);
	}
}
