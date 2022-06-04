package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieTypeYToCovidM extends MovieCountYToCovidM {
    private String type;

    public MovieTypeYToCovidM(Integer year, String type, Long movieCount, List<CovidMonthReport> covidStats) {
        super(year, movieCount, covidStats);
        this.type = type;
    }
}
