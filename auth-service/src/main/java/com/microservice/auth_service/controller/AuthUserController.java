package com.microservice.auth_service.controller;


import com.microservice.auth_service.dto.request.LoginRequest;
import com.microservice.auth_service.dto.request.UserSaveRequest;
import com.microservice.auth_service.dto.response.ResponseMessage;
import com.microservice.auth_service.dto.response.TokenDTO;
import com.microservice.auth_service.dto.response.TokenVerifyResponse;
import com.microservice.auth_service.model.AuthUser;
import com.microservice.auth_service.model.RefreshToken;
import com.microservice.auth_service.repository.AuthUserRepository;
import com.microservice.auth_service.repository.RefreshTokenRespository;
import com.microservice.auth_service.service.AuthUserService;
import com.microservice.auth_service.service.RefreshTokenService;
import com.microservice.auth_service.util.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthUserRepository authUserRepository;

    @PostMapping("/register")
    public ResponseMessage save(@RequestBody @Valid UserSaveRequest request) {
        authUserService.saveAuthUser(request);
        return new ResponseMessage("Check your mailbox");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authUserService.authenticate(loginRequest));
    }

    @PostMapping("/refreshToken/{refreshToken}")
    public ResponseEntity<Map<String, String>> refreshToken(@PathVariable String refreshToken) {
        return ResponseEntity.ok(refreshTokenService.getToken(refreshToken));
    }

    @PostMapping("/verify")
    public ResponseEntity<TokenVerifyResponse> verify(@RequestParam String token) {
        return ResponseEntity.ok(tokenService.verifyToken(token));
    }

    @PatchMapping("/activation/{token}")
    public ResponseMessage activate(@PathVariable String token) {
        authUserService.activate(token);
        return new ResponseMessage("User activated successfully");
    }

    @DeleteMapping("/delete/{authId}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Long authId) {
       authUserService.delete(authId);
        return  new ResponseEntity<>(new ResponseMessage("User deleted successfully"),
                HttpStatus.OK);
    }
}
