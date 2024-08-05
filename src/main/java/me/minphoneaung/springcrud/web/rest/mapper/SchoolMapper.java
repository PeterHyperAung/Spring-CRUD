package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import org.springframework.stereotype.Service;

@Service
public class SchoolMapper {
    public School toSchool(SchoolDto dto) {
        var school = new School();
        school.setName(dto.name());
        return school;
    }

    public SchoolDto toSchoolDto(School school) {
        return new SchoolDto(
                school.getId(),
                school.getName()
        );
    }

}
