package me.minphoneaung.springcrud.students;

import me.minphoneaung.springcrud.schools.School;

import java.time.LocalDate;

public record StudentResponseDto (
        Integer id,
        String name,
        String email,
        Integer age,
        School school,

        LocalDate startedAt
) {
}
