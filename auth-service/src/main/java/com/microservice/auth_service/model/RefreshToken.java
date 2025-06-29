package com.microservice.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jdk.jfr.Timestamp;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RefreshToken extends BaseModel {
    private String refreshToken;
    @OneToOne
    private AuthUser authUser;
    private Date expirationDate;
}
