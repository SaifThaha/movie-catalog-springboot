package com._xtech.MovieApi.controller;

import com._xtech.MovieApi.dto.MovieRequestDto;
import com._xtech.MovieApi.dto.MovieResponseDto;
import com._xtech.MovieApi.exceptions.EmptyFileException;
import com._xtech.MovieApi.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @PostMapping("/save-movie")
    public ResponseEntity<MovieResponseDto> saveMovie(@RequestPart MultipartFile multipartFile,
                                                      @RequestPart String requestDto) throws IOException, EmptyFileException {
        if (multipartFile.isEmpty()){
            throw new EmptyFileException("File is empty! Please upload another file");
        }
        MovieRequestDto movieRequestDto = convertToDto(requestDto);
        MovieResponseDto saveMovie = movieService.saveMovie(movieRequestDto,multipartFile);
        return new ResponseEntity<>(saveMovie, HttpStatus.CREATED);
    }

    @GetMapping("{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public MovieResponseDto getMovieById(@PathVariable("movieId") Long movieId){
        return movieService.getMovie(movieId);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieResponseDto> getAllMovies(){
        return movieService.getAllMovie();
    }

    @PutMapping("/update-movie/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public MovieResponseDto updateMovie(@PathVariable("movieId") Long movieId,
                                        @RequestPart MultipartFile multipartFile,
                                        @RequestPart String requestDto) throws IOException {
        if(multipartFile.isEmpty()) multipartFile = null;
        MovieRequestDto movieRequestDto = convertToDto(requestDto);
        return movieService.updateMovie(movieId,movieRequestDto,multipartFile);
    }

    @GetMapping("/movies")
    public Page<MovieResponseDto> getAllMovies(@RequestParam int pageNumber,
                                               @RequestParam int pageSize,
                                               @RequestParam String field){
        return movieService.getAllMovie(pageNumber,pageSize,field);
    }

    @DeleteMapping("/delete-movie/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable("movieId") Long movieId) throws IOException {
        movieService.deleteMovie(movieId);
    }

    private MovieRequestDto convertToDto(String requestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestDto,MovieRequestDto.class);
    }
}
