package com.fenrir.moviecovidanalysis.repository;

import com.fenrir.moviecovidanalysis.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
