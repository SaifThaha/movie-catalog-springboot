package com._xtech.MovieApi.exceptions;

public class FileExistsException extends RuntimeException{
    public FileExistsException(String message){
        super(message);
    }
}
