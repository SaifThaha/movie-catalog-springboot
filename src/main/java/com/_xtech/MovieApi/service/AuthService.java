package com._xtech.MovieApi.service;

import com._xtech.MovieApi.dto.AccessTokenRequestDto;
import com._xtech.MovieApi.dto.AuthResponseDto;
import com._xtech.MovieApi.dto.LoginRequestDto;

public interface AuthService {
    AuthResponseDto loginUser(LoginRequestDto loginRequestDto);
    AuthResponseDto getNewAccessToken(AccessTokenRequestDto accessTokenRequestDto);
}
