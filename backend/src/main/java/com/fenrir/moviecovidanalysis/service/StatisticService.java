package com.fenrir.moviecovidanalysis.service;

import com.fenrir.moviecovidanalysis.dto.statistics.*;
import com.fenrir.moviecovidanalysis.model.MoviePremiereMonthCount;
import com.fenrir.moviecovidanalysis.model.statistics.*;
import com.fenrir.moviecovidanalysis.repository.MovieMonthCountRepository;
import com.fenrir.moviecovidanalysis.repository.StatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class StatisticService {
    private StatisticsRepository statisticsRepository;
    private MovieMonthCountRepository movieMonthCountRepository;

    public List<MovieCountYToCovidY> getMovieCountYToCovidY() {
        List<MovieYearStatistics> movieStatistics = statisticsRepository.findMovieCountYearByYear();
        List<CovidYearStatistics> covidStatistics = statisticsRepository.findCovidYearByYearStatistics();
        List<MovieCountYToCovidY> result = new ArrayList<>();

        for (MovieYearStatistics movieStats : movieStatistics) {
            CovidYearStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .findFirst()
                    .orElse(new CovidYearStatistics(movieStats.getYear(), 0L, 0L));
            result.add(new MovieCountYToCovidY(
                    movieStats.getYear(),
                    movieStats.getCount(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MovieCountYToCovidM> getMovieCountYToCovidM() {
        List<MovieYearStatistics> movieStatistics = statisticsRepository.findMovieCountYearByYear();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MovieCountYToCovidM> result = new ArrayList<>();

        for (MovieYearStatistics movieStats : movieStatistics) {
            List<CovidMonthReport> covidReports = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .map(c -> new CovidMonthReport(c.getMonth(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidMonthReport::month))
                    .toList();
            result.add(new MovieCountYToCovidM(
                    movieStats.getYear(),
                    movieStats.getCount(),
                    covidReports
            ));
        }
        return result;
    }

    public List<MovieGenresYToCovidY> getMovieCountByGenresYToCovidY() {
        List<MovieGenresYearStatistics> movieStatistics = statisticsRepository.findMovieCountByGenresYearByYear();
        List<CovidYearStatistics> covidStatistics = statisticsRepository.findCovidYearByYearStatistics();
        List<MovieGenresYToCovidY> result = new ArrayList<>();

        for (MovieGenresYearStatistics movieStats : movieStatistics) {
            CovidYearStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .findFirst()
                    .orElse(new CovidYearStatistics(movieStats.getYear(), 0L, 0L));
            result.add(new MovieGenresYToCovidY(
                    movieStats.getYear(),
                    movieStats.getGenre(),
                    movieStats.getCount(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MovieGenresYToCovidM> getMovieCountByGenresYToCovidM() {
        List<MovieGenresYearStatistics> movieStatistics = statisticsRepository.findGenrePopularityYearByYear();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MovieGenresYToCovidM> result = new ArrayList<>();

        for (MovieGenresYearStatistics movieStats : movieStatistics) {
            List<CovidMonthReport> covidReports = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .map(c -> new CovidMonthReport(c.getMonth(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidMonthReport::month))
                    .toList();
            result.add(new MovieGenresYToCovidM(
                    movieStats.getYear(),
                    movieStats.getGenre(),
                    movieStats.getCount(),
                    covidReports
            ));
        }
        return result;
    }

    public List<MovieGenresYToCovidY> getMoviePopularityByGenresYToCovidY() {
        List<MovieGenresYearStatistics> movieStatistics = statisticsRepository.findGenrePopularityYearByYear();
        List<CovidYearStatistics> covidStatistics = statisticsRepository.findCovidYearByYearStatistics();
        List<MovieGenresYToCovidY> result = new ArrayList<>();

        for (MovieGenresYearStatistics movieStats : movieStatistics) {
            CovidYearStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .findFirst()
                    .orElse(new CovidYearStatistics(movieStats.getYear(), 0L, 0L));
            result.add(new MovieGenresYToCovidY(
                    movieStats.getYear(),
                    movieStats.getGenre(),
                    movieStats.getCount(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MovieGenresYToCovidM> getMoviePopularityByGenresYToCovidM() {
        List<MovieGenresYearStatistics> movieStatistics = statisticsRepository.findGenrePopularityYearByYear();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MovieGenresYToCovidM> result = new ArrayList<>();

        for (MovieGenresYearStatistics movieStats : movieStatistics) {
            List<CovidMonthReport> covidReports = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .map(c -> new CovidMonthReport(c.getMonth(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidMonthReport::month))
                    .toList();
            result.add(new MovieGenresYToCovidM(
                    movieStats.getYear(),
                    movieStats.getGenre(),
                    movieStats.getCount(),
                    covidReports
            ));
        }
        return result;
    }

    public List<MovieTypeYToCovidY> getMovieCountByTypeYToCovidY() {
        List<MovieTypeYearStatistics> movieStatistics = statisticsRepository.findMovieCountByTypeYearByYear();
        List<CovidYearStatistics> covidStatistics = statisticsRepository.findCovidYearByYearStatistics();
        List<MovieTypeYToCovidY> result = new ArrayList<>();

        for (MovieTypeYearStatistics movieStats : movieStatistics) {
            CovidYearStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .findFirst()
                    .orElse(new CovidYearStatistics(movieStats.getYear(), 0L, 0L));
            result.add(new MovieTypeYToCovidY(
                    movieStats.getYear(),
                    movieStats.getType(),
                    movieStats.getCount(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MovieTypeYToCovidM> getMovieCountByTypeYToCovidM() {
        List<MovieTypeYearStatistics> movieStatistics = statisticsRepository.findMovieCountByTypeYearByYear();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MovieTypeYToCovidM> result = new ArrayList<>();

        for (MovieTypeYearStatistics movieStats : movieStatistics) {
            List<CovidMonthReport> covidReports = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .map(c -> new CovidMonthReport(c.getMonth(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidMonthReport::month))
                    .toList();
            result.add(new MovieTypeYToCovidM(
                    movieStats.getYear(),
                    movieStats.getType(),
                    movieStats.getCount(),
                    covidReports
            ));
        }
        return result;
    }

    public List<MovieTypeYToCovidY> getMoviePopularityByTypeYToCovidY() {
        List<MovieTypeYearStatistics> movieStatistics = statisticsRepository.findMovieTypePopularityYearByYear();
        List<CovidYearStatistics> covidStatistics = statisticsRepository.findCovidYearByYearStatistics();
        List<MovieTypeYToCovidY> result = new ArrayList<>();

        for (MovieTypeYearStatistics movieStats : movieStatistics) {
            CovidYearStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .findFirst()
                    .orElse(new CovidYearStatistics(movieStats.getYear(), 0L, 0L));
            result.add(new MovieTypeYToCovidY(
                    movieStats.getYear(),
                    movieStats.getType(),
                    movieStats.getCount(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MovieTypeYToCovidM> getMoviePopularityByTypeYToCovidM() {
        List<MovieTypeYearStatistics> movieStatistics = statisticsRepository.findMovieTypePopularityYearByYear();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MovieTypeYToCovidM> result = new ArrayList<>();

        for (MovieTypeYearStatistics movieStats : movieStatistics) {
            List<CovidMonthReport> covidReports = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .map(c -> new CovidMonthReport(c.getMonth(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidMonthReport::month))
                    .toList();
            result.add(new MovieTypeYToCovidM(
                    movieStats.getYear(),
                    movieStats.getType(),
                    movieStats.getCount(),
                    covidReports
            ));
        }
        return result;
    }

    public List<MovieRateYToCovidY> getMovieAverageRateYToCovidY() {
        List<MovieAverageRateYearStatistics> movieStatistics = statisticsRepository.findMovieAverageRatingYearByYear();
        List<CovidYearStatistics> covidStatistics = statisticsRepository.findCovidYearByYearStatistics();
        List<MovieRateYToCovidY> result = new ArrayList<>();

        for (MovieAverageRateYearStatistics movieStats : movieStatistics) {
            CovidYearStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .findFirst()
                    .orElse(new CovidYearStatistics(movieStats.getYear(), 0L, 0L));
            result.add(new MovieRateYToCovidY(
                    movieStats.getYear(),
                    movieStats.getRate(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MovieRateYToCovidM> getMovieAverageRateYToCovidM() {
        List<MovieAverageRateYearStatistics> movieStatistics = statisticsRepository.findMovieAverageRatingYearByYear();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MovieRateYToCovidM> result = new ArrayList<>();

        for (MovieAverageRateYearStatistics movieStats : movieStatistics) {
            List<CovidMonthReport> covidReports = covidStatistics.stream()
                    .filter(c -> c.getYear().equals(movieStats.getYear()))
                    .map(c -> new CovidMonthReport(c.getMonth(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidMonthReport::month))
                    .toList();
            result.add(new MovieRateYToCovidM(
                    movieStats.getYear(),
                    movieStats.getRate(),
                    covidReports
            ));
        }
        return result;
    }

    public List<MoviePremierCountMToCovidM> getMoviePremiereCountMToCovidM() {
        List<MoviePremiereMonthCount> movieStatistics = movieMonthCountRepository.findAll();
        List<CovidMonthStatistics> covidStatistics = statisticsRepository.findCovidMonthByMonthStatistics();
        List<MoviePremierCountMToCovidM> result = new ArrayList<>();

        for (MoviePremiereMonthCount movieStats : movieStatistics) {
            CovidMonthStatistics covidStats = covidStatistics.stream()
                    .filter(c -> c.getYear() == movieStats.getDate().getYear() && c.getMonth() == movieStats.getDate().getMonthValue())
                    .findFirst()
                    .orElse(new CovidMonthStatistics(movieStats.getDate().getYear(), movieStats.getDate().getMonthValue(), 0L, 0L));
            result.add(new MoviePremierCountMToCovidM(
                    covidStats.getMonth(),
                    covidStats.getYear(),
                    movieStats.getCount().longValue(),
                    covidStats.getCases(),
                    covidStats.getDeaths()
            ));
        }
        return result;
    }

    public List<MoviePremierCountMToCovidD> getMoviePremiereCountMToCovidD() {
        List<MoviePremiereMonthCount> movieStatistics = movieMonthCountRepository.findAll();
        List<CovidDayStatistics> covidStatistics = statisticsRepository.findCovidDayByDayStatistics();
        List<MoviePremierCountMToCovidD> result = new ArrayList<>();

        for (MoviePremiereMonthCount movieStats : movieStatistics) {
            List<CovidDayReport> covidStats = covidStatistics.stream()
                    .filter(c -> (c.getDate().getYear() + 1900) == movieStats.getDate().getYear()
                            && (c.getDate().getMonth() + 1) == movieStats.getDate().getMonthValue())
                    .map(c -> new CovidDayReport(c.getDate().getMonth(), c.getDate().getDay(), c.getCases(), c.getDeaths()))
                    .sorted(Comparator.comparingInt(CovidDayReport::day))
                    .toList();
            result.add(new MoviePremierCountMToCovidD(
                    movieStats.getDate().getMonthValue(),
                    movieStats.getDate().getYear(),
                    movieStats.getCount(),
                    covidStats
            ));
        }
        return result;
    }
}
