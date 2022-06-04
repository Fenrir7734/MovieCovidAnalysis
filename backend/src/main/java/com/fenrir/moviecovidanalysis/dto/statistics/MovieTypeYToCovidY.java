package com.fenrir.moviecovidanalysis.dto.statistics;

import lombok.Getter;

@Getter
public class MovieTypeYToCovidY extends MovieCountYToCovidY {
    private String type;

    public MovieTypeYToCovidY(Integer year, String type, Long movieCount, Long cases, Long deaths) {
        super(year, movieCount, cases, deaths);
        this.type = type;
    }
}
