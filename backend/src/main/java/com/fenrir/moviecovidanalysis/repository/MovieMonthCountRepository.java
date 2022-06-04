package com.fenrir.moviecovidanalysis.repository;

import com.fenrir.moviecovidanalysis.model.MoviePremiereMonthCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieMonthCountRepository extends JpaRepository<MoviePremiereMonthCount, Long> {
}
