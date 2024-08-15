package com._xtech.MovieApi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail handleMovieNotFoundException(MovieNotFoundException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,exception.getMessage());
        problemDetail.setTitle("Movie Not Found");
        problemDetail.setInstance(URI.create("/movies/" + exception.getMovieId()));
        return problemDetail;
    }

    @ExceptionHandler(FileExistsException.class)
    public ProblemDetail handleFileExistsException(FileExistsException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("File Already Exists");
        return problemDetail;
    }

    @ExceptionHandler(EmptyFileException.class)
    public ProblemDetail handleEmptyFileException(EmptyFileException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("File Is Empty");
        return problemDetail;
    }
}
