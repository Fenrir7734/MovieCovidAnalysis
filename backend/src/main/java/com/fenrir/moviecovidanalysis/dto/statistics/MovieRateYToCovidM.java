package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieRateYToCovidM {
    private Integer year;
    private Double movieAverageRate;
    private List<CovidMonthReport> covidStats;

    public MovieRateYToCovidM(Integer year, Double movieAverageRate, List<CovidMonthReport> covidStats) {
        this.year = year;
        this.movieAverageRate = movieAverageRate;
        this.covidStats = covidStats;
    }
}
