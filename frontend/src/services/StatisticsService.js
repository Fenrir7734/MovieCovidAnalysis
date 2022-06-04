import http from '../http-common'
import authHeader from "@/services/AuthHeader";

class StatisticsService {
    movieCount() {
        return http.get('/stats/movie-count', { headers: authHeader() })
    }

    moviePremiere() {
        return http.get('/stats/movie-premiere-count', { headers: authHeader() })
    }

    movieGenreCount() {
        return http.get('/stats/movie-genre-count', { headers: authHeader() })
    }

    movieGenrePopularity() {
        return http.get('/stats/movie-genre-popularity', { headers: authHeader() })
    }

    movieTypeCount() {
        return http.get('/stats/movie-type-count', { headers: authHeader() })
    }

    movieTypePopularity() {
        return http.get('/stats/movie-type-popularity', { headers: authHeader() })
    }

    movieAverageRate() {
        return http.get('/stats/movie-avg-rate', { headers: authHeader() })
    }
}

export default new StatisticsService()