package com.fenrir.moviecovidanalysis.loader;

import com.fenrir.moviecovidanalysis.model.Movie2;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.conversions.Conversions;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@AllArgsConstructor
public class InitailMovieDataWriter implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(InitailMovieDataWriter.class);

    private final TsvLoader tsvLoader = new TsvLoader();
    private final Map<String, Movie2> movies = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Movie dataset...");
        parseMovies();
        parseRates();
        removeInvalidMovies();

        saveToFile();
        logger.info("Written");
    }

    private void parseMovies() throws FileNotFoundException {
        Path path = Path.of("data/title_basic.tsv");
        ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
            @Override
            public void rowProcessed(Object[] objects, ParsingContext parsingContext) {
                if (objects[5] != null
                        && (Integer) objects[5] > 2015
                        && objects[0] != null
                        && objects[1] != null
                        && objects[2] != null
                        && objects[4] != null
                        && objects[7] != null
                        && objects[8] != null) {

                    String titleId = (String) objects[0];
                    String type = (String) objects[1];
                    String primaryTitle = (String) objects[2];
                    String originalTitle = (String) objects[3];
                    Boolean isAdult = (Boolean) objects[4];
                    Integer startYear = (Integer) objects[5];
                    Integer endYear = (Integer) objects[6];
                    Integer runtimeMinutes = (Integer) objects[7];
                    String genres = (String) objects[8];

                    Movie2 movie = new Movie2();
                    movie.setTitleId(titleId);
                    movie.setTitleType(type);
                    movie.setPrimaryTitle(primaryTitle);
                    movie.setOriginalTitle(originalTitle);
                    movie.setAdult(isAdult);
                    movie.setStartYear(startYear);
                    movie.setEndYear(endYear);
                    movie.setRuntimeMinutes(runtimeMinutes);
                    movie.setGenres(genres);

                    movies.put(titleId, movie);
                }
            }
        };
        rowProcessor.convertAll(Conversions.toNull("\\N"));
        rowProcessor.convertFields(Conversions.toInteger()).set("startYear", "endYear", "runtimeMinutes");
        rowProcessor.convertFields(Conversions.toBoolean("1", "0")).set("isAdult");
        tsvLoader.load(path.toString(), rowProcessor);
    }

    public void parseRates() throws FileNotFoundException {
        Path path = Path.of("data/title_ratings.tsv");
        ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
            @Override
            public void rowProcessed(Object[] objects, ParsingContext parsingContext) {
                Movie2 movie = movies.getOrDefault((String) objects[0], null);
                if (movie != null && objects[1] != null && objects[2] != null) {
                    movie.setAverageRating((Double) objects[1]);
                    movie.setNumVotes((Integer) objects[2]);
                } else if (movie != null) {
                    movies.remove((String) objects[0]);
                }
            }
        };
        rowProcessor.convertFields(Conversions.toDouble()).set("averageRating");
        rowProcessor.convertFields(Conversions.toInteger()).set("numVotes");
        tsvLoader.load(path.toString(), rowProcessor);
    }

    private void removeInvalidMovies() {
        List<String> toRemove = movies.values()
                .stream()
                .filter(m -> m.getNumVotes() == null || m.getAverageRating() == null)
                .map(Movie2::getTitleId)
                .toList();
        for (String titleId: toRemove) {
            movies.remove(titleId);
        }
    }

    private void saveToFile() throws IOException {
        Random random = new Random();
        List<String> toRemove = new ArrayList<>();

        for (String titleId: movies.keySet()) {
            if (random.nextInt() % 2 == 0) {
                toRemove.add(titleId);
            }
        }

        for (String s : toRemove) {
            movies.remove(s);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/home/fenrir/Documents/Test_environment/integracja_data/title_basic_out.csv"))) {
            for (Movie2 movie: movies.values()) {
                StringBuilder builder = new StringBuilder();
                builder.append(movie.getTitleId())
                        .append("\t")
                        .append(movie.getTitleType())
                        .append("\t")
                        .append(movie.getPrimaryTitle())
                        .append("\t")
                        .append(movie.getOriginalTitle())
                        .append("\t")
                        .append(movie.isAdult() ? "1" : "0")
                        .append("\t")
                        .append(movie.getStartYear())
                        .append("\t")
                        .append(movie.getEndYear())
                        .append("\t")
                        .append(movie.getRuntimeMinutes())
                        .append("\t")
                        .append(movie.getGenres())
                        .append("\n");
                writer.write(builder.toString());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/home/fenrir/Documents/Test_environment/integracja_data/title_ratings.tsv"))) {
            for (Movie2 movie: movies.values()) {
                StringBuilder builder = new StringBuilder();
                builder.append(movie.getTitleId())
                        .append("\t")
                        .append(movie.getAverageRating())
                        .append("\t")
                        .append(movie.getNumVotes())
                        .append("\n");
                writer.write(builder.toString());
            }
        }
    }

}
