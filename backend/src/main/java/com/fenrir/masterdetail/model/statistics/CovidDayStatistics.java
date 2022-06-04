package com.fenrir.masterdetail.model.statistics;

import lombok.Data;

import java.util.Date;

@Data
public class CovidDayStatistics {
    private Date date;
    private Long cases;
    private Long deaths;

    public CovidDayStatistics(Date date, Long cases, Long deaths) {
        this.date = date;
        this.cases = cases;
        this.deaths = deaths;
    }
}
