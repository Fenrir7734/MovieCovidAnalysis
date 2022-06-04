package com.fenrir.masterdetail.repository;

import com.fenrir.masterdetail.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
