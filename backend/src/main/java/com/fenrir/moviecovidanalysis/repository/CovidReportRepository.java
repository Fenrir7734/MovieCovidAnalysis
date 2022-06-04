package com.fenrir.moviecovidanalysis.repository;

import com.fenrir.moviecovidanalysis.model.CovidReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CovidReportRepository extends JpaRepository<CovidReport, Long> {
    List<CovidReport> getCovidReportByCountry_IdOrderByDateAsc(Long countryId);
}
