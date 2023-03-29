package com.example.Waffle.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="refresh_token")
    private String refreshToken;

    private String email;

    public TokenEntity(String token, String email) {
        this.refreshToken = token;
        this.email = email;
    }

    public TokenEntity updateToken(String token) {
        this.refreshToken = token;
        return this;
    }
}