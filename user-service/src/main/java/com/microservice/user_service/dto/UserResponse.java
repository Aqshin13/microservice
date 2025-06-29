package com.microservice.user_service.dto;


import com.microservice.user_service.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String fullName;
    private Long authenticatedUserId;


    public UserResponse(User user) {
        this.fullName = user.getFullName();
        this.authenticatedUserId = user.getAuthenticatedUserId();
    }
}
