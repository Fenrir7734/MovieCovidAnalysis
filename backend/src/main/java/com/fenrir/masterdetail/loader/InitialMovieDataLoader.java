package com.fenrir.masterdetail.loader;

import com.fenrir.masterdetail.model.Genre;
import com.fenrir.masterdetail.model.Movie;
import com.fenrir.masterdetail.model.TitleType;
import com.fenrir.masterdetail.repository.GenreRepository;
import com.fenrir.masterdetail.repository.MovieRepository;
import com.fenrir.masterdetail.repository.TitleTypeRepository;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.conversions.Conversions;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class InitialMovieDataLoader implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(InitialMovieDataLoader.class);

    private MovieRepository movieRepository;
    private GenreRepository genreRepository;
    private TitleTypeRepository titleTypeRepository;

    private final TsvLoader tsvLoader = new TsvLoader();

    private final Map<String, Movie> movies = new HashMap<>();
    private final Map<String, Genre> genres = new HashMap<>();
    private final Map<String, TitleType> types = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Movie dataset...");
        parseMovies();
        parseRates();
        removeInvalidMovies();

        titleTypeRepository.saveAll(types.values());
        genreRepository.saveAll(genres.values());
        movieRepository.saveAll(movies.values());

        movies.clear();
        genres.clear();
        types.clear();
        logger.info("Movie dataset loaded.");
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
                    String title = (String) objects[2];
                    Boolean isAdult = (Boolean) objects[4];
                    Integer startYear = (Integer) objects[5];
                    Integer endYear = (Integer) objects[6];
                    Integer runtimeMinutes = (Integer) objects[7];
                    String[] genres = ((String) objects[8]).split(",");
                    movies.put(titleId, new Movie(
                            titleId,
                            title,
                            isAdult,
                            startYear,
                            endYear,
                            runtimeMinutes,
                            null,
                            null,
                            parseTitleType(type),
                            parseGenres(genres)
                    ));
                }
            }
        };
        rowProcessor.convertAll(Conversions.toNull("\\N", "null"));
        rowProcessor.convertFields(Conversions.toInteger()).set("startYear", "endYear", "runtimeMinutes");
        rowProcessor.convertFields(Conversions.toBoolean("1", "0")).set("isAdult");
        tsvLoader.load(path.toString(), rowProcessor);
    }

    public void parseRates() throws FileNotFoundException {
        Path path = Path.of("data/title_ratings.tsv");
        ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
            @Override
            public void rowProcessed(Object[] objects, ParsingContext parsingContext) {
                Movie movie = movies.getOrDefault((String) objects[0], null);
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

    private Set<Genre> parseGenres(String[] genresToParse) {
        return Arrays.stream(genresToParse)
                .peek(g -> genres.putIfAbsent(g, new Genre(g)))
                .map(genres::get)
                .collect(Collectors.toSet());
    }

    private TitleType parseTitleType(String titleTypeToParse) {
        types.putIfAbsent(titleTypeToParse, new TitleType(titleTypeToParse));
        return types.get(titleTypeToParse);
    }

    private void removeInvalidMovies() {
        List<String> toRemove = movies.values()
                .stream()
                .filter(m -> m.getNumVotes() == null || m.getAverageRating() == null)
                .map(Movie::getTitleId)
                .toList();
        for (String titleId: toRemove) {
            movies.remove(titleId);
        }
    }
}
