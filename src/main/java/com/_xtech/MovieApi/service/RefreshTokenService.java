package com._xtech.MovieApi.service;

import com._xtech.MovieApi.entity.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);
    RefreshToken verifyRefreshToken(String refreshToken);
}
