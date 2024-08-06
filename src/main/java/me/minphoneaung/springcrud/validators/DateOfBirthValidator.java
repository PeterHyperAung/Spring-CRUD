package me.minphoneaung.springcrud.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.minphoneaung.springcrud.annotations.ValidateDateOfBirth;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.Period;

public class DateOfBirthValidator implements ConstraintValidator<ValidateDateOfBirth, LocalDate> {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    @Override
    public void initialize(ValidateDateOfBirth constraintAnnotation) {
    }

//    @Override
//    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
//        if (dob == null) {
//            return false;
//        }
//
//        try {
//            LocalDate dateOfBirth = LocalDate.parse(dob, DATE_FORMATTER);
//            LocalDate now = LocalDate.now();
//
//            if (dateOfBirth.isAfter(now)) {
//                return false;
//            }
//
//            int age = Period.between(dateOfBirth, now).getYears();
//            return age >= 0 && age <= 120;
//        } catch (DateTimeParseException e) {
//            return false;
//        }
//    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        // Example validation: Ensure the date is not in the future
        return dateOfBirth != null && !dateOfBirth.isAfter(LocalDate.now());
    }
}
