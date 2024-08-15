package com._xtech.MovieApi.serviceImpl;

import com._xtech.MovieApi.Repository.MovieRepository;
import com._xtech.MovieApi.dto.MovieRequestDto;
import com._xtech.MovieApi.dto.MovieResponseDto;
import com._xtech.MovieApi.entity.Movie;
import com._xtech.MovieApi.exceptions.FileExistsException;
import com._xtech.MovieApi.exceptions.MovieNotFoundException;
import com._xtech.MovieApi.service.FileService;
import com._xtech.MovieApi.service.MovieService;

import com._xtech.MovieApi.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final FileService fileService;
    private final MovieRepository movieRepository;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;
    @Value("${project.poster}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;
    @Override
    public MovieResponseDto saveMovie(MovieRequestDto movieRequestDto, MultipartFile multipartFile) throws IOException {
        if(Files.exists(Paths.get(path + File.separator + multipartFile.getOriginalFilename()))){
            throw new FileExistsException("File Already Exists!");
        }
        //upload the file
        String fileName = fileService.uploadFile(path,multipartFile);

        //convert dto to entity
        Movie movie = modelMapper.map(movieRequestDto, Movie.class);

        //set the fileName as the value of field "poster"
        movie.setPoster(fileName);

        //save entity to database
        Movie savedMovie = movieRepository.save(movie);

        //generate the poster url
        String posterUrl = baseUrl + "/file/" + fileName;

        //convert entity to dto
        MovieResponseDto responseDto = modelMapper.map(savedMovie,MovieResponseDto.class);
        responseDto.setPosterUrl(posterUrl);
        return responseDto;
    }

    @Override
    public MovieResponseDto getMovie(Long movieId) {
        //check if movie exists by id
        boolean movieExist = validationUtils.isMovieExist(movieId);

        if(movieExist){
             Movie movie = movieRepository.findById(movieId).get();

            //generate the poster url
             String posterUrl = baseUrl + "/file/" + movie.getPoster();

             MovieResponseDto movieResponseDto = modelMapper.map(movie,MovieResponseDto.class);
             movieResponseDto.setPosterUrl(posterUrl);
             return movieResponseDto;
        }else throw new MovieNotFoundException(movieId);
    }

    @Override
    public List<MovieResponseDto> getAllMovie() {
        List<Movie> movieList = movieRepository.findAll();
        List<MovieResponseDto> movieResponseDtos = new ArrayList<>();

        for (Movie movie:movieList) {
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MovieResponseDto movieResponseDto = modelMapper.map(movie,MovieResponseDto.class);
            movieResponseDto.setPosterUrl(posterUrl);
            movieResponseDtos.add(movieResponseDto);
        }
        return movieResponseDtos;
    }

    @Override
    public MovieResponseDto updateMovie(Long movieId, MovieRequestDto movieRequestDto, MultipartFile multipartFile) throws IOException {
        boolean movieExist = validationUtils.isMovieExist(movieId);
        if(!movieExist){
            throw new MovieNotFoundException(movieId);
        }
        Movie existingMovie = movieRepository.findById(movieId).get();
        if (multipartFile != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + existingMovie.getPoster()));
            String fileName = fileService.uploadFile(path,multipartFile);
            existingMovie.setPoster(fileName);
        }

        //map dto to entity and update
        Movie updatedMovie = modelMapper.map(movieRequestDto,Movie.class);
        updatedMovie.setMovieId(existingMovie.getMovieId());
        updatedMovie.setPoster(existingMovie.getPoster());

        //saved the updated entity
        Movie savedMovie = movieRepository.save(updatedMovie);

        //generate the poster url
        String posterUrl = baseUrl + "/file/" + savedMovie.getPoster();

        //convert the entity to dto and return
        MovieResponseDto movieResponseDto = modelMapper.map(savedMovie,MovieResponseDto.class);
        movieResponseDto.setPosterUrl(posterUrl);
        return movieResponseDto;
    }

    @Override
    public Page<MovieResponseDto> getAllMovie(int pageNumber, int pageSize, String field) {
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize, Sort.by(field).ascending());
        Page<Movie> moviePage = movieRepository.findAll(pageRequest);

        return moviePage.map(movie ->{
            MovieResponseDto movieResponseDto = modelMapper.map(movie,MovieResponseDto.class);
            movieResponseDto.setPosterUrl(baseUrl + "/file/" + movie.getPoster());
            return movieResponseDto;
        });
    }

    @Override
    public void deleteMovie(Long movieId) throws IOException {
        //check if the movie exists
        boolean movieExist = validationUtils.isMovieExist(movieId);
        if(!movieExist){
            throw new MovieNotFoundException(movieId);
        }

        Movie movie = movieRepository.findById(movieId).get();

        //delete file associated with the entity
        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));

        //delete the entity
        movieRepository.delete(movie);
    }
}
