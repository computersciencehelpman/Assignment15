package com.coderscampus.Assignment15.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateConstraint {
    String message() default "You must be between 18 and 100 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
