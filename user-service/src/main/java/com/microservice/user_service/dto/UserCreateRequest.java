package com.microservice.user_service.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String phoneNumber;
    @NotNull
    private Long authId;
}
