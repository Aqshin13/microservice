package com.microservice.auth_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint(validatedBy = {ValidateUsername.class })
@Target({  FIELD})
@Retention(RUNTIME)
public @interface UniqueUsername {

    String message() default "Username is in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
