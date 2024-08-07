package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.web.rest.dto.DataTableResponseDto;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapper extends BaseMapper<School, SchoolDto>{
}
