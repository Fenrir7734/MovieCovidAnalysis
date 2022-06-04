package com.fenrir.moviecovidanalysis.controller;

import com.fenrir.moviecovidanalysis.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/stats",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class StatisticController {
    private StatisticService statisticService;

    @GetMapping(path = "/movie-count")
    public ResponseEntity<?> getMovieCount(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("year")) {
            return ResponseEntity.ok(statisticService.getMovieCountYToCovidY());
        } else if (period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMovieCountYToCovidM());
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(path = "/movie-premiere-count")
    public ResponseEntity<?> getMoviePremiereCount(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMoviePremiereCountMToCovidM());
        } else if (period.get().equals("day")) {
            return ResponseEntity.ok(statisticService.getMoviePremiereCountMToCovidD());
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(path = "/movie-genre-count")
    public ResponseEntity<?> getGenreCount(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("year")) {
            return ResponseEntity.ok(statisticService.getMovieCountByGenresYToCovidY());
        } else if (period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMovieCountByGenresYToCovidM());
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(path = "/movie-genre-popularity")
    public ResponseEntity<?> getGenrePopularity(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("year")) {
            return ResponseEntity.ok(statisticService.getMoviePopularityByGenresYToCovidY());
        } else if (period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMoviePopularityByGenresYToCovidM());
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(path = "/movie-type-count")
    public ResponseEntity<?> getTypeCount(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("year")) {
            return ResponseEntity.ok(statisticService.getMovieCountByTypeYToCovidY());
        } else if (period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMovieCountByTypeYToCovidM());
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(path = "/movie-type-popularity")
    public ResponseEntity<?> getTypePopularity(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("year")) {
            return ResponseEntity.ok(statisticService.getMoviePopularityByTypeYToCovidY());
        } else if (period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMoviePopularityByTypeYToCovidM());
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping(path = "/movie-avg-rate")
    public ResponseEntity<?> getAverageRate(@RequestParam Optional<String> period) {
        if (period.isEmpty() || period.get().equals("year")) {
            return ResponseEntity.ok(statisticService.getMovieAverageRateYToCovidY());
        } else if (period.get().equals("month")) {
            return ResponseEntity.ok(statisticService.getMovieAverageRateYToCovidM());
        } else {
            return ResponseEntity.ok(null);
        }
    }

}
