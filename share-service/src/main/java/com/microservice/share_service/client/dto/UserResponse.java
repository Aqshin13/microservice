package com.microservice.share_service.client.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String fullName;
    private Long authenticatedUserId;
}
