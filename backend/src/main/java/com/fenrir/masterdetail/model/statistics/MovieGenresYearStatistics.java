package com.fenrir.masterdetail.model.statistics;

import lombok.Data;

@Data
public class MovieGenresYearStatistics {
    private String genre;
    private Integer year;
    private Long count;

    public MovieGenresYearStatistics(String genre, Integer year, Long count) {
        this.genre = genre;
        this.year = year;
        this.count = count;
    }
}
