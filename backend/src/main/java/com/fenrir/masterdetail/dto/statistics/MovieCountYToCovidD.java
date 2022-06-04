package com.fenrir.masterdetail.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieCountYToCovidD {
    private Integer year;
    private Long movieCount;
    private List<CovidDayReport> covidStats;

    public MovieCountYToCovidD(Integer year, Long movieCount, List<CovidDayReport> covidStats) {
        this.year = year;
        this.movieCount = movieCount;
        this.covidStats = covidStats;
    }
}
