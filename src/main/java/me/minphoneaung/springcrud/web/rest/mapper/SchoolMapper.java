package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper extends BaseMapper<School, SchoolDto>{
}
