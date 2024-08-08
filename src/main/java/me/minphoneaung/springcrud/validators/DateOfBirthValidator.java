package me.minphoneaung.springcrud.validators;

import me.minphoneaung.springcrud.annotations.ValidateDateOfBirth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateOfBirthValidator implements ConstraintValidator<ValidateDateOfBirth, LocalDate> {

    @Override
    public void initialize(ValidateDateOfBirth constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        return dateOfBirth != null && !dateOfBirth.isAfter(LocalDate.now());
    }
}
