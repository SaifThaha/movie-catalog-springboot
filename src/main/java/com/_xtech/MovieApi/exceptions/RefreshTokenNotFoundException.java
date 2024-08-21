package com._xtech.MovieApi.exceptions;

public class RefreshTokenNotFoundException extends RuntimeException{
    public RefreshTokenNotFoundException(String message){
        super(message);
    }
}
