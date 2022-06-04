package com.fenrir.moviecovidanalysis.repository;

import com.fenrir.moviecovidanalysis.model.statistics.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class StatisticsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CovidDayStatistics> findCovidDayByDayStatistics() {
        Query query = entityManager.createQuery(
                "SELECT new com.fenrir.moviecovidanalysis.model.statistics.CovidDayStatistics(cr.date, SUM(cr.newCases), SUM(cr.newDeaths)) " +
                        "FROM CovidReport AS cr " +
                        "GROUP BY cr.date " +
                        "ORDER BY cr.date");
        return query.getResultList();
    }

    public List<CovidMonthStatistics> findCovidMonthByMonthStatistics() {
        Query query = entityManager.createQuery(
                "SELECT new com.fenrir.moviecovidanalysis.model.statistics.CovidMonthStatistics(YEAR(cr.date) AS y, MONTH(cr.date) AS m, SUM(cr.newCases), SUM(cr.newDeaths)) " +
                        "FROM CovidReport AS cr " +
                        "GROUP BY YEAR(cr.date), MONTH(cr.date) " +
                        "ORDER BY y, m");
        return query.getResultList();
    }

    public List<CovidYearStatistics> findCovidYearByYearStatistics() {
        Query query = entityManager.createQuery(
                "SELECT new com.fenrir.moviecovidanalysis.model.statistics.CovidYearStatistics(YEAR(cr.date) AS y, SUM(cr.newCases), SUM(cr.newDeaths)) " +
                        "FROM CovidReport AS cr " +
                        "GROUP BY YEAR(cr.date) " +
                        "ORDER BY y"
        );
        return query.getResultList();
    }

    public List<MovieGenresYearStatistics> findMovieCountByGenresYearByYear() {
        Query query = entityManager.createNativeQuery(
                "SELECT g.name, m.start_year, COUNT(m.id) " +
                        "FROM movie AS m " +
                        "INNER JOIN movie_genre AS mg " +
                        "ON m.id = mg.movie_id " +
                        "INNER JOIN genre AS g " +
                        "ON mg.genre_id = g.id " +
                        "GROUP BY g.name, m.start_year " +
                        "ORDER BY g.name, m.start_year");

        List<Object[]> list = query.getResultList();
        List<MovieGenresYearStatistics> movieGenresYearStatistics = new ArrayList<>();
        for (Object[] object : list) {
            movieGenresYearStatistics.add(new MovieGenresYearStatistics(
                    (String) object[0],
                    (Integer) object[1],
                    ((BigInteger) object[2]).longValue()
            ));
        }
        return movieGenresYearStatistics;
    }

    public List<MovieGenresYearStatistics> findGenrePopularityYearByYear() {
        Query query = entityManager.createNativeQuery(
                "SELECT g.name, m.start_year, SUM(m.num_votes) " +
                        "FROM movie AS m " +
                        "INNER JOIN movie_genre AS mg " +
                        "ON m.id = mg.movie_id " +
                        "INNER JOIN genre AS g " +
                        "ON mg.genre_id = g.id " +
                        "GROUP BY g.name, m.start_year " +
                        "ORDER BY g.name, m.start_year");

        List<Object[]> list = query.getResultList();
        List<MovieGenresYearStatistics> movieGenresYearStatistics = new ArrayList<>();
        for (Object[] object : list) {
            movieGenresYearStatistics.add(new MovieGenresYearStatistics(
                    (String) object[0],
                    (Integer) object[1],
                    ((BigDecimal) object[2]).longValue()
            ));
        }
        return movieGenresYearStatistics;
    }

    public List<MovieTypeYearStatistics> findMovieCountByTypeYearByYear() {
        Query query = entityManager.createNativeQuery(
                "SELECT t.name, m.start_year, COUNT(m.id) " +
                        "FROM movie as m " +
                        "INNER JOIN title_type AS t " +
                        "ON m.title_type_id = t.id " +
                        "GROUP BY t.name, m.start_year " +
                        "ORDER BY t.name, m.start_year"
        );

        List<Object[]> list = query.getResultList();
        List<MovieTypeYearStatistics> movieTypeYearStatistics = new ArrayList<>();
        for (Object[] object : list) {
            movieTypeYearStatistics.add(new MovieTypeYearStatistics(
                    (String) object[0],
                    (Integer) object[1],
                    ((BigInteger) object[2]).longValue()
            ));
        }
        return movieTypeYearStatistics;
    }

    public List<MovieTypeYearStatistics> findMovieTypePopularityYearByYear() {
        Query query = entityManager.createNativeQuery(
                "SELECT t.name, m.start_year, SUM(num_votes) " +
                        "FROM movie AS m " +
                        "INNER JOIN title_type AS t " +
                        "ON m.title_type_id = t.id " +
                        "GROUP BY t.name, m.start_year " +
                        "ORDER BY t.name, m.start_year"
        );

        List<Object[]> list = query.getResultList();
        List<MovieTypeYearStatistics> movieTypeYearStatistics = new ArrayList<>();
        for (Object[] object : list) {
            movieTypeYearStatistics.add(new MovieTypeYearStatistics(
                    (String) object[0],
                    (Integer) object[1],
                    ((BigDecimal) object[2]).longValue()
            ));
        }
        return movieTypeYearStatistics;
    }

    public List<MovieYearStatistics> findMovieCountYearByYear() {
        Query query = entityManager.createQuery(
                "SELECT new com.fenrir.moviecovidanalysis.model.statistics.MovieYearStatistics(m.startYear, COUNT(m.id)) " +
                        "FROM Movie AS m " +
                        "GROUP BY m.startYear " +
                        "ORDER BY m.startYear"
        );
        return query.getResultList();
    }

    public List<MovieYearStatistics> findMoviePopularityYearByYear() {
        Query query = entityManager.createQuery(
                "SELECT new com.fenrir.moviecovidanalysis.model.statistics.MovieYearStatistics(m.startYear, SUM(m.numVotes)) " +
                        "FROM Movie AS m " +
                        "GROUP BY m.startYear " +
                        "ORDER BY m.startYear"
        );
        return query.getResultList();
    }

    public List<MovieAverageRateYearStatistics> findMovieAverageRatingYearByYear() {
        Query query = entityManager.createQuery(
                "SELECT new com.fenrir.moviecovidanalysis.model.statistics.MovieAverageRateYearStatistics(m.startYear, AVG(m.averageRating)) " +
                        "FROM Movie AS m " +
                        "GROUP BY m.startYear " +
                        "ORDER BY m.startYear"
        );
        return query.getResultList();
    }
}
