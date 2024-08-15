package com._xtech.MovieApi.exceptions;

public class MovieNotFoundException extends RuntimeException{
    private final Long movieId;
    public MovieNotFoundException(Long movieId){
        super("Movie with ID " + movieId + " not found");
        this.movieId = movieId;
    }

    public Long getMovieId() {
        return movieId;
    }
}
