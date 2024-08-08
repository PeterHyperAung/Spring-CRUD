package me.minphoneaung.springcrud.web.rest.dto;

import lombok.*;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
        Integer id;

        @NotEmpty(message = "Name should not be empty")
        String name;

        @NotEmpty(message = "Principal name should not be empty")
        String principal;
}

