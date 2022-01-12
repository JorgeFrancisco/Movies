package com.joorgeit.movies.config.resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

import com.joorgeit.movies.model.MovieCsv;
import com.joorgeit.movies.util.Util;

public class CsvMoviesHelper {

	public static List<MovieCsv> csvToMovies(InputStream is) {
		CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';').withEscape('\\').withQuoteMode(QuoteMode.NONE)
				.withRecordSeparator("\n").withFirstRecordAsHeader().withHeader().withIgnoreEmptyLines()
				.withIgnoreHeaderCase().withTrim();

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, csvFormat);) {

			List<MovieCsv> movies = new ArrayList<MovieCsv>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				Long year = Util.isInvalidText(csvRecord.get("year")) ? 0L
						: Util.isNumber(csvRecord.get("year")) ? Long.valueOf(csvRecord.get("year")) : 0;

				String title = Util.isInvalidText(csvRecord.get("title")) ? "" : csvRecord.get("title");

				List<String> studios = Util.isInvalidText(csvRecord.get("studios")) ? null
						: split(csvRecord.get("studios"));

				List<String> producers = Util.isInvalidText(csvRecord.get("producers")) ? null
						: split(csvRecord.get("producers"));

				Boolean winner = Util.isInvalidText(csvRecord.get("winner")) ? false
						: "yes".equals(csvRecord.get("winner").trim());

				movies.add(new MovieCsv(year, title, studios, producers, winner));
			}

			return movies;
		} catch (Exception e) {
			throw new RuntimeException("Fail to parse movies from csvfile: " + e.getMessage());
		}
	}

	private static List<String> split(String stringCsv) {
		String strings = stringCsv.trim();

		if (Util.isInvalidText(strings) == false) {
			strings = strings.replaceAll(",(\\s)+and(\\s)+", ",");
			strings = strings.replaceAll(",and(\\s)+", ",");
			strings = strings.replaceAll("(\\s)+and,", ",");
			strings = strings.replaceAll("(\\s)+and(\\s)+,", ",");
			strings = strings.replaceAll("(\\s)+and(\\s)+", ",");

			return Arrays.asList(strings.split(","));
		}

		return null;
	}
}
