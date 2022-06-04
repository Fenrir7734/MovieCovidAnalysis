package com.fenrir.moviecovidanalysis.dto.statistics;

public record CovidDayReport(int month, int day, Long cases, Long death) {
}
