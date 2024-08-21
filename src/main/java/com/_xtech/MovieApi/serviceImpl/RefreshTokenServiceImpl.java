package com._xtech.MovieApi.serviceImpl;

import com._xtech.MovieApi.Repository.RefreshTokenRepository;
import com._xtech.MovieApi.Repository.UserRepository;
import com._xtech.MovieApi.entity.RefreshToken;
import com._xtech.MovieApi.entity.User;
import com._xtech.MovieApi.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(180);
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    
    @Override
    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with email: "+ username);
        }else {
            RefreshToken refreshToken = user.getRefreshToken();
            if(refreshToken == null){
                refreshToken = RefreshToken.builder()
                        .refreshToken(UUID.randomUUID().toString())
                        .expirationTime(Date.from(Instant.now().plusMillis(VALIDITY)).toInstant())
                        .user(user)
                        .build();

                refreshTokenRepository.save(refreshToken);
            }
            return refreshToken;
        }
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->new RuntimeException("Refresh Token Not Found"));

        if (IsTokenExpired(token)){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token not found");
        }
        return token;
    }

    private boolean IsTokenExpired(RefreshToken refreshToken){
        return refreshToken.getExpirationTime().isBefore(Date.from(Instant.now()).toInstant());
    }
}
