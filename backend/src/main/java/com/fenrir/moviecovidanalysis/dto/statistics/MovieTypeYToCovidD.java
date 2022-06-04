package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieTypeYToCovidD extends MovieCountYToCovidD {
    private String type;

    public MovieTypeYToCovidD(Integer year, String type, Long movieCount, List<CovidDayReport> covidStats) {
        super(year, movieCount, covidStats);
        this.type = type;
    }
}
