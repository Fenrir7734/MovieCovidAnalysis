package com.fenrir.masterdetail.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MoviePremierCountMToCovidD {
    private int month;
    private int year;
    private int movieCount;
    private List<CovidDayReport> covidStats;

    public MoviePremierCountMToCovidD(int month, int year, int movieCount, List<CovidDayReport> covidStats) {
        this.month = month;
        this.year = year;
        this.movieCount = movieCount;
        this.covidStats = covidStats;
    }
}
