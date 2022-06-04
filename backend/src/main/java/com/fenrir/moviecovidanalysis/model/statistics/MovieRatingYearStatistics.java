package com.fenrir.moviecovidanalysis.model.statistics;

import lombok.Data;

@Data
public class MovieRatingYearStatistics {
    private Integer year;
    private Double avgRating;

    public MovieRatingYearStatistics(Integer year, Double avgRating) {
        this.year = year;
        this.avgRating = avgRating;
    }
}
