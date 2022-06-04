package com.fenrir.masterdetail.repository;

import com.fenrir.masterdetail.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
