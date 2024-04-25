package com.marchenko.clearsolutiontest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeLimitValidator.class)
public @interface AgeLimit {
    String message() default "User must be at least {value} years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
