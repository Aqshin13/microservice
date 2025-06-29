package com.microservice.auth_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExcHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiError> handleException(CustomException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setStatusCode(e.getStatusCode());
        return ResponseEntity.status(e.getStatusCode()).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage("Validation Error");
        apiError.setStatusCode(400);
        var errors =
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                                (x, y) -> x));
        apiError.setValidationErrors(errors);
        return ResponseEntity.status(400).body(apiError);
    }

}
