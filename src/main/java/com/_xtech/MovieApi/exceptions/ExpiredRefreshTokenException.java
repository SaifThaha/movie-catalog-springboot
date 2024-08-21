package com._xtech.MovieApi.exceptions;

public class ExpiredRefreshTokenException extends RuntimeException{
    public ExpiredRefreshTokenException(String message){
        super(message);
    }
}
