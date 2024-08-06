package me.minphoneaung.springcrud.web.rest.dto;

import java.util.List;

public record DataTableResponseDto<T>(
        Integer draw,
        long recordsTotal,
        long recordsFiltered,
        List<T> data

) {
}
