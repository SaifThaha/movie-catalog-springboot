package com._xtech.MovieApi.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String extractUsername(String JwtToken);
    boolean isTokenValid(String jwtToken);
}
