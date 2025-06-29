package com.microservice.auth_service.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private int statusCode;
    private String message;
    private Map<String,String> validationErrors;
}
