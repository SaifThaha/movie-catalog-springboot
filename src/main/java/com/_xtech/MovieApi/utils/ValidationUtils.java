package com._xtech.MovieApi.utils;

import com._xtech.MovieApi.Repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtils {
    private final MovieRepository movieRepository;

    public boolean isMovieExist(Long movieId){
        return movieRepository.existsById(movieId);
    }
}
