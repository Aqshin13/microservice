package com.microservice.share_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Share extends BaseModel {
    private String text;
    @Column(nullable = false)
    private Long authenticatedUserId;

}
