package com.microservice.auth_service.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String token;
    private long authUserId;
    private String refreshToken;
}
