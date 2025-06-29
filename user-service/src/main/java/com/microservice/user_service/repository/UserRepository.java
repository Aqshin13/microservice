package com.microservice.user_service.repository;

import com.microservice.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    void deleteByAuthenticatedUserId(Long id);

    Optional<User> findByAuthenticatedUserId(Long id);
}
