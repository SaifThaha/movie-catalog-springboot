package com._xtech.MovieApi.service;

import com._xtech.MovieApi.dto.RegistrationRequestDto;
import com._xtech.MovieApi.dto.RegistrationResponseDto;
import com._xtech.MovieApi.entity.User;

public interface RegistrationService {
    RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto);
}
