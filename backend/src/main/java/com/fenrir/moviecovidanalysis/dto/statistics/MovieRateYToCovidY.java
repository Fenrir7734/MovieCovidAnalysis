package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieRateYToCovidY {
    private Integer year;
    private Double movieAverageRate;
    private Long cases;
    private Long deaths;

    public MovieRateYToCovidY(Integer year, Double movieAverageRate, Long cases, Long deaths) {
        this.year = year;
        this.movieAverageRate = movieAverageRate;
        this.cases = cases;
        this.deaths = deaths;
    }
}
