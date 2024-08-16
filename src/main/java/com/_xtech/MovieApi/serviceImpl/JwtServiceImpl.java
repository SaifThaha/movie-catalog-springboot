package com._xtech.MovieApi.serviceImpl;

import com._xtech.MovieApi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "6c5f22498c4ee73ee6fa2b8c94c3fd6334655bb0381586d55f018a86b27af820";
    private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(30);
    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    @Override
    public String extractUsername(String JwtToken) {
        Claims claims = getClaims(JwtToken);
        return claims.getSubject();
    }

    @Override
    public boolean isTokenValid(String jwtToken) {
        Claims claims = getClaims(jwtToken);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    private Claims getClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
