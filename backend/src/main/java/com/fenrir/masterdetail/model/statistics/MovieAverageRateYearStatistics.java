package com.fenrir.masterdetail.model.statistics;

import lombok.Data;

@Data
public class MovieAverageRateYearStatistics {
    private Integer year;
    private Double rate;

    public MovieAverageRateYearStatistics(Integer year, Double rate) {
        this.year = year;
        this.rate = rate;
    }
}
