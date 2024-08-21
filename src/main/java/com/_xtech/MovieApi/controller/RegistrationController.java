package com._xtech.MovieApi.controller;

import com._xtech.MovieApi.dto.RegistrationRequestDto;
import com._xtech.MovieApi.dto.RegistrationResponseDto;
import com._xtech.MovieApi.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationResponseDto registerUser(@Valid @RequestBody RegistrationRequestDto registrationRequestDto){
        return registrationService.registerUser(registrationRequestDto);
    }
}
