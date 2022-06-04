package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

@Getter
public class MovieCountYToCovidY {
    private Integer year;
    private Long movieCount;
    private Long cases;
    private Long deaths;

    public MovieCountYToCovidY(Integer year, Long movieCount, Long cases, Long deaths) {
        this.year = year;
        this.movieCount = movieCount;
        this.cases = cases;
        this.deaths = deaths;
    }
}
