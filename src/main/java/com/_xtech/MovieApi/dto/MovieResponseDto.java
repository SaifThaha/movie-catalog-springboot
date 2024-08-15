package com._xtech.MovieApi.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    private Long movieId;
    private String title;
    private String director;
    private String studioName;
    private Set<String> movieCast;
    private Integer releaseYear;
    private String poster;
    private String posterUrl;
}
