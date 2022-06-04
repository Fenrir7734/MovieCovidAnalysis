package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieRateYToCovidD {
    private Integer year;
    private Double movieAverageRate;
    private List<CovidDayReport> covidStats;

    public MovieRateYToCovidD(Integer year, Double movieAverageRate, List<CovidDayReport> covidStats) {
        this.year = year;
        this.movieAverageRate = movieAverageRate;
        this.covidStats = covidStats;
    }
}
