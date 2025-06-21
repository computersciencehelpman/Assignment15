package com.coderscampus.Assignment15.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) return false;

        LocalDate today = LocalDate.now();
        int age = Period.between(birthDate, today).getYears();

        return age >= 18 && age <= 100;
    }
}
