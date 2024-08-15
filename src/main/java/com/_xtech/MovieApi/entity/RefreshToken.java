package com._xtech.MovieApi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @Column(nullable = false, length = 500)
    private String refreshToken;
    @Column(nullable = false)
    private Instant expirationTime;
    @OneToOne
    private User user;
}
