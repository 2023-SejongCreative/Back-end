package com.example.Waffle.repository;

import com.example.Waffle.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByEmail(String email);
}