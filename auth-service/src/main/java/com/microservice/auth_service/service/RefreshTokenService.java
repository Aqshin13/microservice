package com.microservice.auth_service.service;

import com.microservice.auth_service.exception.CustomException;
import com.microservice.auth_service.model.AuthUser;
import com.microservice.auth_service.model.RefreshToken;
import com.microservice.auth_service.repository.RefreshTokenRespository;
import com.microservice.auth_service.util.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRespository refreshTokenRespository;
    private final TokenService tokenService;

    public Map<String, String> getToken(String refreshToken) {
        HashMap<String, String> map = new HashMap<>();
        Optional<RefreshToken> refreshTokenDB =
                refreshTokenRespository.findByRefreshToken(refreshToken);
        if (refreshTokenDB.isEmpty()) {
            throw new CustomException("Invalid Refresh Token", 400);
        }
        if (tokenService.expireToken(refreshTokenDB.get())) {
            throw new CustomException(" Refresh Token is expired", 499);

        }
        String token = tokenService.createToken(refreshTokenDB.get().getAuthUser());
        map.put("token", token);
        return map;

    }


}
