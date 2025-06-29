package com.microservice.auth_service.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
public class AuthUser extends BaseModel {
    private String userName;
    private String password;
    private boolean active=false;
    @OneToOne(mappedBy = "authUser",cascade = CascadeType.ALL)
    private RefreshToken refreshToken;
    private String activationToken;
}
