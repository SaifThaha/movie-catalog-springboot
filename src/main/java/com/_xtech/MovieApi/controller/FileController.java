package com._xtech.MovieApi.controller;

import com._xtech.MovieApi.service.FileService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @Value("${project.poster}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile multipartFile) throws IOException {
        String uploadedFile = fileService.uploadFile(path,multipartFile);
        return ResponseEntity.ok("File Uploaded: " + uploadedFile);
    }

    @GetMapping("/{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = fileService.getResourceFile(path,fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile,response.getOutputStream());
    }
}
