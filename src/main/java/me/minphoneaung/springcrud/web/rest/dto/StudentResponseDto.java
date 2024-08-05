package me.minphoneaung.springcrud.web.rest.dto;

import me.minphoneaung.springcrud.entities.School;

import java.time.LocalDate;

public record StudentResponseDto (
        Integer id,
        String name,
        String email,
        Integer age,
        School school,

        Integer schoolId,

        LocalDate startedAt
) {
}
