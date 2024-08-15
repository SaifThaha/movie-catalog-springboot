package com._xtech.MovieApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "Director cannot be blank")
    private String director;
    @Column(nullable = false)
    @NotBlank(message = "Studio name cannot be blank")
    private String studioName;
    @ElementCollection
    @CollectionTable(name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id",nullable = false))
    @Column(name = "cast_member")
    private Set<String> movieCast;
    @Column(nullable = false)
    private Integer releaseYear;
    @Column(nullable = false)
    @NotBlank(message = "Poster cannot be blank")
    private String poster;
}
