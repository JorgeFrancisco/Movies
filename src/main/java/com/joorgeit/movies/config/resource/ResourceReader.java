package com.joorgeit.movies.config.resource;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.joorgeit.movies.model.MovieCsv;

public class ResourceReader {

	Logger logger = LoggerFactory.getLogger(ResourceReader.class);

	public List<MovieCsv> loadMovies(String fileName) {
		try {
			logger.info("Loading movies from file = " + fileName + "...");

			InputStream inputStream = new ClassPathResource(fileName).getInputStream();

			return CsvMoviesHelper.csvToMovies(inputStream);
		} catch (Exception e) {
			logger.error("Error occurred while loading movies from file = " + fileName, e);

			return Collections.emptyList();
		}
	}
}
