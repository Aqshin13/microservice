package com.microservice.user_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ApiError {
    private int statusCode;
    private String message;
    private Map<String,String> validationErrors;
}
