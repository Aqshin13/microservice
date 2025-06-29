package com.microservice.share_service.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShareSaveRequest {

    @NotEmpty
    private String text;

}
