package me.minphoneaung.springcrud.web.controller;

import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import org.springframework.ui.Model;

public interface PaginableViewController {
    public <T> void addPaginationAttribute(Model model, PaginationResponseDto<T> resource, String resourceName);
}
