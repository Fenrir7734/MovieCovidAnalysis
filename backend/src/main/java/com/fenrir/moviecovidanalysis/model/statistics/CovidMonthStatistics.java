package com.fenrir.moviecovidanalysis.model.statistics;

import lombok.Data;

@Data
public class CovidMonthStatistics {
    private Integer year;
    private Integer month;
    private Long cases;
    private Long deaths;

    public CovidMonthStatistics(Integer year, Integer month, Long cases, Long deaths) {
        this.year = year;
        this.month = month;
        this.cases = cases;
        this.deaths = deaths;
    }
}
