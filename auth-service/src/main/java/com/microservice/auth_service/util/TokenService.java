package com.microservice.auth_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.auth_service.dto.request.LoginRequest;
import com.microservice.auth_service.dto.response.TokenDTO;
import com.microservice.auth_service.dto.response.TokenVerifyResponse;
import com.microservice.auth_service.exception.CustomException;
import com.microservice.auth_service.model.AuthUser;
import com.microservice.auth_service.model.RefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    SecretKey key = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());
    ObjectMapper mapper = new ObjectMapper();


    public String createToken(AuthUser user) {
        TokenSubject tokenSubject = new TokenSubject(user.getAuthUserId(),
                user.isActive());
        try {
            String subject = mapper.writeValueAsString(tokenSubject);
            return Jwts.builder().setSubject(subject).signWith(key)
                    .setExpiration(new Date(new Date().getTime() + 30000)).
                    compact();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;


    }


    public TokenVerifyResponse verifyToken(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            Jws<Claims> claims = parser.parseClaimsJws(token);
            var subject = claims.getBody().getSubject();
            var tokenSubject = mapper.readValue(subject, TokenSubject.class);
            TokenVerifyResponse user = new TokenVerifyResponse();
            user.setUserAuthId(tokenSubject.id());
            user.setActive(tokenSubject.active());
            System.out.println("Token is verified");
            return user;
        } catch (SignatureException | JsonProcessingException | MalformedJwtException e) {
            System.out.println(e.getMessage());
            throw new CustomException("Invalid token", 401);
        } catch (ExpiredJwtException e) {
            throw new CustomException("Token is expired ", 489);
        }
    }


    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }


    public boolean expireToken(RefreshToken refreshToken) {
        return refreshToken.getExpirationDate().before(new Date());
    }


    public static record TokenSubject(long id, boolean active) {
    }


}
