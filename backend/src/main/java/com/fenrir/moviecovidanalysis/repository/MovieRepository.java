package com.fenrir.moviecovidanalysis.repository;

import com.fenrir.moviecovidanalysis.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
