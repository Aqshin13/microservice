package com.microservice.user_service.exception;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CommonException extends RuntimeException {
    private int statusCode;


    public CommonException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
