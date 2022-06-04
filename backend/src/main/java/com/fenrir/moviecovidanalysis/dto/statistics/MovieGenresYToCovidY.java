package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

@Getter
public class MovieGenresYToCovidY extends MovieCountYToCovidY {
    private String genre;

    public MovieGenresYToCovidY(int year, String genre, Long movieCount, Long cases, Long deaths) {
        super(year, movieCount, cases, deaths);
        this.genre = genre;
    }
}
