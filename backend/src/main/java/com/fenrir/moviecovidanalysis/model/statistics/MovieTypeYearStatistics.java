package com.fenrir.moviecovidanalysis.model.statistics;

import lombok.Data;

@Data
public class MovieTypeYearStatistics {
    private String type;
    private Integer year;
    private Long count;

    public MovieTypeYearStatistics(String type, Integer year, Long count) {
        this.type = type;
        this.year = year;
        this.count = count;
    }
}
