package com.fenrir.moviecovidanalysis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CovidReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer newCases;

    @Column(nullable = false)
    private Integer cumulativeCases;

    @Column(nullable = false)
    private Integer newDeaths;

    @Column(nullable = false)
    private Integer cumulativeDeaths;

    @ManyToOne
    private Country country;

    public CovidReport(Date date, Integer newCases, Integer cumulativeCases, Integer newDeaths, Integer cumulativeDeaths, Country country) {
        this.date = date;
        this.newCases = newCases;
        this.cumulativeCases = cumulativeCases;
        this.newDeaths = newDeaths;
        this.cumulativeDeaths = cumulativeDeaths;
        this.country = country;
    }
}
