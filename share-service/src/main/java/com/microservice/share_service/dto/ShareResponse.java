package com.microservice.share_service.dto;

import com.microservice.share_service.client.dto.UserResponse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShareResponse {

    private String text;
    private UserResponse user;

}
