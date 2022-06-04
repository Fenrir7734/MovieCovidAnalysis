package com.fenrir.masterdetail.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieGenresYToCovidD extends MovieCountYToCovidD {
    private String genre;

    public MovieGenresYToCovidD(Integer year, String genre, Long movieCount, List<CovidDayReport> covidStats) {
        super(year, movieCount, covidStats);
        this.genre = genre;
    }
}
