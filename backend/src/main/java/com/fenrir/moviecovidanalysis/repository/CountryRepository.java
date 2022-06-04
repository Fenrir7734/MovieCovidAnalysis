package com.fenrir.moviecovidanalysis.repository;

import com.fenrir.moviecovidanalysis.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country getCountryByName(String name);
}
