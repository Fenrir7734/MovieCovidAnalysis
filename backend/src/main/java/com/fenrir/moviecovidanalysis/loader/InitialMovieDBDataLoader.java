package com.fenrir.moviecovidanalysis.loader;

import com.fenrir.moviecovidanalysis.model.MoviePremiereMonthCount;
import com.fenrir.moviecovidanalysis.repository.MovieMonthCountRepository;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@Component
public class InitialMovieDBDataLoader implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(InitialMovieDBDataLoader.class);

    private MovieMonthCountRepository movieMonthCountRepository;

    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(Duration.ofSeconds(40))
            .build();
    private static final String API_KEY = "";

    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Movie count from The Movie Database API...");

        for (int i = 2015; i < 2023; i++) {
            for (int j = 1; j < 13; j++) {
                LocalDate date = LocalDate.of(i, j, 1);
                if (date.isAfter(LocalDate.now())) break;

                HttpResponse<String> response = makeRequest(date);
                parseResult(response.body(), date);
            }
        }

        logger.info("Movie count loaded.");
    }

    private HttpResponse<String> makeRequest(LocalDate date) throws IOException, InterruptedException {
        String month = date.getMonthValue() < 10 ? "0" + date.getMonthValue() : String.valueOf(date.getMonthValue());
        String url = String.format(
                "https://api.themoviedb.org/3/discover/movie?api_key=%s&primary_release_date.gte=%s-%s-01&primary_release_date.lte=%s-%s-%s&with_release_type=1",
                API_KEY,
                date.getYear(),
                month,
                date.getYear(),
                month,
                date.lengthOfMonth());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .timeout(Duration.ofSeconds(40))
                .GET()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void parseResult(String body, LocalDate date) {
        JSONObject object = new JSONObject(body);
        int count = object.getInt("total_results");
        MoviePremiereMonthCount movieMonthCount = new MoviePremiereMonthCount(date, count);
        movieMonthCountRepository.save(movieMonthCount);
    }
}
