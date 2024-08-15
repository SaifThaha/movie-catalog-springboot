package com._xtech.MovieApi.service;

import com._xtech.MovieApi.dto.MovieRequestDto;
import com._xtech.MovieApi.dto.MovieResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieResponseDto saveMovie(MovieRequestDto movieRequestDto, MultipartFile multipartFile) throws IOException;
    MovieResponseDto getMovie(Long movieId);
    List<MovieResponseDto> getAllMovie();
    MovieResponseDto updateMovie(Long movieId, MovieRequestDto movieRequestDto, MultipartFile multipartFile) throws IOException;
    Page<MovieResponseDto> getAllMovie(int pageNumber, int pageSize, String field);
    void deleteMovie(Long movieId) throws IOException;
}
