package com.fenrir.masterdetail.repository;

import com.fenrir.masterdetail.model.MoviePremiereMonthCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieMonthCountRepository extends JpaRepository<MoviePremiereMonthCount, Long> {
}
