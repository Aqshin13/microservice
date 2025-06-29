package com.microservice.api_gateway.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenVerifyResponse {


    private  long userAuthId;
    private boolean active;

}
