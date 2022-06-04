package com.fenrir.moviecovidanalysis.model.statistics;

import lombok.Data;

@Data
public class MovieYearStatistics {
    private Integer year;
    private Long count;

    public MovieYearStatistics(Integer year, Long count) {
        this.year = year;
        this.count = count;
    }
}
