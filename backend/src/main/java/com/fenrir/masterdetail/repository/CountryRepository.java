package com.fenrir.masterdetail.repository;

import com.fenrir.masterdetail.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country getCountryByName(String name);
}
