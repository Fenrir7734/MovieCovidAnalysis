package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieGenresYToCovidM extends MovieCountYToCovidM {
    private String genre;

    public MovieGenresYToCovidM(Integer year, String genre, Long movieCount, List<CovidMonthReport> covidStats) {
        super(year, movieCount, covidStats);
        this.genre = genre;
    }
}
