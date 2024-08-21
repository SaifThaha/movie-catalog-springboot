package com._xtech.MovieApi.controller;

import com._xtech.MovieApi.dto.AccessTokenRequestDto;
import com._xtech.MovieApi.dto.AuthResponseDto;
import com._xtech.MovieApi.dto.LoginRequestDto;
import com._xtech.MovieApi.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return authService.loginUser(loginRequestDto);
    }

    @PostMapping("/accessToken")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto getNewAccessToken(@RequestBody AccessTokenRequestDto accessTokenRequestDto) {
        return authService.getNewAccessToken(accessTokenRequestDto);
    }
}
