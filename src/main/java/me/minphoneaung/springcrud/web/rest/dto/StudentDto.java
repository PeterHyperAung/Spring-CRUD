package me.minphoneaung.springcrud.web.rest.dto;


import lombok.*;
import me.minphoneaung.springcrud.annotations.ValidateDateOfBirth;
import me.minphoneaung.springcrud.entities.School;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
        Integer id;
        @NotEmpty(message = "Name is required")
        String name;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        String email;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @NotNull(message = "Date of birth is required")
        @ValidateDateOfBirth
        LocalDate dateOfBirth;
        Integer schoolId;
        School school;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startedAt;
}
