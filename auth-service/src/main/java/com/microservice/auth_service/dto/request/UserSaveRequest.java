package com.microservice.auth_service.dto.request;


import com.microservice.auth_service.validation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserSaveRequest {

    @NotEmpty
    @UniqueUsername
    @Size(min = 4, max = 10)
    private String userName;
    @NotEmpty
    @Size(min = 8)
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private String fullName;
    @NotEmpty
    private String phoneNumber;
}
