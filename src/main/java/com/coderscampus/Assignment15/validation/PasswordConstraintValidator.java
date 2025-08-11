package com.coderscampus.Assignment15.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }
}
