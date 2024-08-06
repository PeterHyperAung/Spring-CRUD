package me.minphoneaung.springcrud.web.rest.dto;

import jakarta.validation.constraints.NotEmpty;

public record SchoolDto(

        Integer id,

        @NotEmpty(message = "Name should not be empty")
        String name,

        @NotEmpty(message = "Principal name should not be empty")
        String principal
) {
}
