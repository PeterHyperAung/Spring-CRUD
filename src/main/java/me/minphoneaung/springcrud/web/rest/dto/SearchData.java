package me.minphoneaung.springcrud.web.rest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchData {

    private LocalDate fromDate;
    private LocalDate toDate;
    private int status;
}
