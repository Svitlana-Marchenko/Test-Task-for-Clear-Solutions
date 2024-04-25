package com.marchenko.clearsolutiontest.validation;

import com.marchenko.clearsolutiontest.model.User;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeLimitValidator implements ConstraintValidator<AgeLimit, User> {
    @Value("${user.min.age}")
    private int ageLimit;

    @Override
    public void initialize(AgeLimit constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user.getBirthDate() == null) {
            return true;
        }
        return user.getBirthDate().plusYears(ageLimit).isBefore(LocalDate.now());
    }
}

