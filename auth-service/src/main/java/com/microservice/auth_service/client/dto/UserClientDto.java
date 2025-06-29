package com.microservice.auth_service.client.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserClientDto {

    private String email;
    private String fullName;
    private String phoneNumber;
    private Long authId;
}
