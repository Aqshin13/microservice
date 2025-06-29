package com.microservice.user_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseModel {
    private String email;
    private String fullName;
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private Long authenticatedUserId;
}
