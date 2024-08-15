package com._xtech.MovieApi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Director cannot be blank")
    private String director;
    @NotBlank(message = "Studio name cannot be blank")
    private String studioName;
    private Set<String> movieCast;
    private Integer releaseYear;
}
