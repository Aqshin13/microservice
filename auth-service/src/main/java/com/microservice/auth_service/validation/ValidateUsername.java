package com.microservice.auth_service.validation;

import com.microservice.auth_service.repository.AuthUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class ValidateUsername implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return authUserRepository.findByUserName(value).isEmpty();
    }
}
