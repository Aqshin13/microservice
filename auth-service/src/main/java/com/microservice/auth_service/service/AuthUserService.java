package com.microservice.auth_service.service;

import com.microservice.auth_service.client.UserServiceClient;
import com.microservice.auth_service.client.dto.UserClientDto;
import com.microservice.auth_service.dto.EmailDTO;
import com.microservice.auth_service.dto.request.LoginRequest;
import com.microservice.auth_service.dto.request.UserSaveRequest;
import com.microservice.auth_service.dto.response.TokenDTO;
import com.microservice.auth_service.exception.CustomException;
import com.microservice.auth_service.model.AuthUser;
import com.microservice.auth_service.model.RefreshToken;
import com.microservice.auth_service.repository.AuthUserRepository;
import com.microservice.auth_service.repository.RefreshTokenRespository;
import com.microservice.auth_service.util.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserService {


    private final AuthUserRepository authUserRepository;
    private final UserServiceClient userServiceClient;
    private final TokenService tokenService;
    private final RefreshTokenRespository tokenRepository;

    private final KafkaProducer kafkaProducer;

    @Transactional(rollbackOn = Exception.class)
    public void saveAuthUser(UserSaveRequest userSaveRequest) {
        AuthUser authUser = AuthUser.builder()
                .userName(userSaveRequest.getUserName())
                .password(hashPassword(userSaveRequest.getPassword()))
                .activationToken(UUID.randomUUID().toString())
                .build();
        AuthUser auth = authUserRepository.save(authUser);
        System.out.println(auth.getAuthUserId() + " " + authUser.getActivationToken());
        UserClientDto userClientDto = UserClientDto.builder()
                .authId(auth.getAuthUserId())
                .email(userSaveRequest.getEmail())
                .fullName(userSaveRequest.getFullName())
                .phoneNumber(userSaveRequest.getPhoneNumber())
                .build();
        try {
            userServiceClient.createUser(userClientDto);
        } catch (Exception e) {
            throw new RuntimeException("Feign Error: " + e.getMessage());
        }
        EmailDTO emailDTO = EmailDTO.builder()
                .activationToken(authUser.getActivationToken())
                .email(userSaveRequest.getEmail())
                .build();

        System.out.println(emailDTO.getActivationToken());
        kafkaProducer.sendMessage(emailDTO);


    }


    public TokenDTO authenticate(LoginRequest loginRequest) {
        Optional<AuthUser> authUser = authUserRepository
                .findByUserName(loginRequest.getUserName());
        if (authUser.isEmpty()) {
            throw new CustomException("Invalid username or password", 400);
        }
        if (!authUser.get().getPassword().equals(hashPassword(loginRequest.getPassword()))) {
            throw new CustomException("Invalid username or password", 400);
        }
        RefreshToken refreshToken = authUser.get().getRefreshToken();

        if (authUser.get().getRefreshToken() == null) {
            refreshToken = new RefreshToken();
            refreshToken.setAuthUser(authUser.get());
        }
        String createdRefreshToken = tokenService.createRefreshToken();
        String token = tokenService.createToken(authUser.get());
        refreshToken.setExpirationDate(Date.from(Instant.now().plusSeconds(120)));
        refreshToken.setRefreshToken(createdRefreshToken);
        tokenRepository.save(refreshToken);
        return new TokenDTO(token, authUser.get().getAuthUserId(), createdRefreshToken);
    }


    public void activate(String token) {
        Optional<AuthUser> optionalAuthUser = authUserRepository.findByActivationToken(token);

        if (optionalAuthUser.isEmpty()) {
            throw new CustomException("Invalid token", 400);
        }
        AuthUser authUser = optionalAuthUser.get();
        authUser.setActive(true);
        authUser.setActivationToken(null);
        authUserRepository.save(authUser);
    }


    public void delete(long id) {
        Optional<AuthUser> optionalAuthUser = authUserRepository.findById(id);
        if (optionalAuthUser.isEmpty()) {
            throw new CustomException("User is not found", 400);
        }
        authUserRepository.deleteById(id);
    }

    private String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
