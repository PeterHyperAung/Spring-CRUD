package me.minphoneaung.springcrud.schools;

import jakarta.validation.constraints.NotEmpty;

public record SchoolDto(

        Integer id,

        @NotEmpty(message = "Name should not be empty")
        String name
) {
}
