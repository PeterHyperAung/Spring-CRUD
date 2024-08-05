package me.minphoneaung.springcrud.dto;

import java.util.List;

public record PaginationResponseDto<T>(
        List<T> data,
        Integer pageNo,
        Integer pageSize,
        Integer totalPages,
        Integer itemsCountInCurrentPage
) {
}
