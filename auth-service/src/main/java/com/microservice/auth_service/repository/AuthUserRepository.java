package com.microservice.auth_service.repository;

import com.microservice.auth_service.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findByUserName(String userName);

    Optional<AuthUser> findByActivationToken(String activationToken);
}
