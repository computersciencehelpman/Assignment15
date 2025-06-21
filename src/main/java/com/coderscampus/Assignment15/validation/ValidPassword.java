package com.coderscampus.Assignment15.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must contain at least one uppercase, one lowercase, one digit, one special character, and be at least 6 characters long";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
