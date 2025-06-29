package com.microservice.auth_service.repository;

import com.microservice.auth_service.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRespository extends JpaRepository<RefreshToken, Long> {


    Optional<RefreshToken> findByRefreshToken(String token);
}
