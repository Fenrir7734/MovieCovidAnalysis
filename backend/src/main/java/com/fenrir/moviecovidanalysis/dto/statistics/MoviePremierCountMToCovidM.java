package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

@Getter
public class MoviePremierCountMToCovidM {
    private int month;
    private int year;
    private Long movieCount;
    private Long cases;
    private Long deaths;

    public MoviePremierCountMToCovidM(int month, int year, Long movieCount, Long cases, Long deaths) {
        this.month = month;
        this.year = year;
        this.movieCount = movieCount;
        this.cases = cases;
        this.deaths = deaths;
    }
}
