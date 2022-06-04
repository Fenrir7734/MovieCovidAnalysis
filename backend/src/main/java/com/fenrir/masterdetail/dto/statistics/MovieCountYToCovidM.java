package com.fenrir.masterdetail.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieCountYToCovidM {
    private Integer year;
    private Long movieCount;
    private List<CovidMonthReport> covidStats;

    public MovieCountYToCovidM(Integer year, Long movieCount, List<CovidMonthReport> covidStats) {
        this.year = year;
        this.movieCount = movieCount;
        this.covidStats = covidStats;
    }
}
