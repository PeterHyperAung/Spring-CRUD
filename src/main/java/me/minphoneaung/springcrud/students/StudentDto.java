package me.minphoneaung.springcrud.students;


import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudentDto(

        @NotEmpty(message = "Name is required")
        String name,

        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotNull(message = "Age is required")
        @Min(value = 3, message = "Age should be greater than 3")
        @Max(value = 100, message = "Age should be less than 100")
        Integer age,
        Integer schoolId,

        LocalDate startedAt
) {
}
