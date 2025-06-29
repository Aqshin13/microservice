package com.microservice.auth_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenVerifyResponse {

    private long userAuthId;
    private boolean active;

}
