package com._xtech.MovieApi.serviceImpl;

import com._xtech.MovieApi.dto.AccessTokenRequestDto;
import com._xtech.MovieApi.dto.AuthResponseDto;
import com._xtech.MovieApi.dto.LoginRequestDto;
import com._xtech.MovieApi.entity.RefreshToken;
import com._xtech.MovieApi.entity.User;
import com._xtech.MovieApi.service.AuthService;
import com._xtech.MovieApi.service.JwtService;
import com._xtech.MovieApi.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @Override
    public AuthResponseDto loginUser(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        ));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        String accessToken = jwtService.generateToken(userDetails);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequestDto.getEmail());

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    @Override
    public AuthResponseDto getNewAccessToken(AccessTokenRequestDto accessTokenRequestDto) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(accessTokenRequestDto.getRefreshToken());

        User user = refreshToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtService.generateToken(userDetails);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
