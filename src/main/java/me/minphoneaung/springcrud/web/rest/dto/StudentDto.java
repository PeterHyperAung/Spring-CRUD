package me.minphoneaung.springcrud.web.rest.dto;


import jakarta.validation.constraints.*;
import me.minphoneaung.springcrud.annotations.ValidateDateOfBirth;

import java.time.LocalDate;

public record StudentDto(

        Integer id,
        @NotEmpty(message = "Name is required")
        String name,

        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Date of birth is required")
        @ValidateDateOfBirth
        LocalDate dateOfBirth,
        Integer schoolId,

        LocalDate startedAt
) {
}
