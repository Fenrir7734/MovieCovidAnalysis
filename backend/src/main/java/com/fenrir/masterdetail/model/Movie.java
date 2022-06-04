package com.fenrir.masterdetail.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String titleId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isAdult;

    @Column(nullable = false)
    private Integer startYear;

    @Column(nullable = true)
    private Integer endYear;

    @Column(nullable = false)
    private Integer runtimeMinutes;

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Integer numVotes;

    @ManyToOne
    private TitleType titleType;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    public Movie(String titleId, String title, boolean isAdult, Integer startYear, Integer endYear, Integer runtimeMinutes, Double averageRating, Integer numVotes, TitleType titleType, Set<Genre> genres) {
        this.titleId = titleId;
        this.title = title;
        this.isAdult = isAdult;
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
        this.titleType = titleType;
        this.genres = genres;
    }
}
