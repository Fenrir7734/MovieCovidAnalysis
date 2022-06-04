package com.fenrir.masterdetail.model.statistics;

import lombok.Data;

@Data
public class CovidYearStatistics {
    private Integer year;
    private Long cases;
    private Long deaths;

    public CovidYearStatistics(Integer year, Long cases, Long deaths) {
        this.year = year;
        this.cases = cases;
        this.deaths = deaths;
    }
}
