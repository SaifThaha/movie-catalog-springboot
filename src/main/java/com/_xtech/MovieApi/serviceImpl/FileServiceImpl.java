package com._xtech.MovieApi.serviceImpl;

import com._xtech.MovieApi.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //get the name of the file
        String fileName = file.getOriginalFilename();

        //get the path of the file
        String filePath = path + File.separator + fileName;

        //create the file object
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //copy the file or upload to the path
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
