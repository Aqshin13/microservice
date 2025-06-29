package com.microservice.user_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class Handler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage("Validation Error");
        apiError.setStatusCode(400);

        var validationErrors = e.getBindingResult().getFieldErrors().stream().collect(
                Collectors
                        .toMap(FieldError::getField,
                                FieldError::getDefaultMessage,
                                (x, y) -> x)
        );
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

}
