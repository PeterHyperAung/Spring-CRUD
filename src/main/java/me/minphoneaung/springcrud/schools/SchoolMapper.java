package me.minphoneaung.springcrud.schools;

import org.springframework.stereotype.Service;

import java.util.Optional;

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
