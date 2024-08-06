package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.web.rest.dto.DataTableResponseDto;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolMapper {
    public School toSchool(SchoolDto dto) {
        var school = new School();
        school.setName(dto.name());
        school.setName(dto.principal());
        return school;
    }

    public SchoolDto toSchoolDto(School school) {
        return new SchoolDto(
                school.getId(),
                school.getName(),
                school.getPrincipal()
        );
    }

    public DataTableResponseDto<SchoolDto> toDataResponseDtoFromSchools(Integer draw, Integer recordsTotal,
                                                             Integer recordsFiltered, List<School> schools) {
        var result = new ArrayList<SchoolDto>();
        for (School school: schools) {
            result.add(toSchoolDto(school));
        }
        return new DataTableResponseDto<>(draw, recordsTotal, recordsFiltered, result);
    }

    public DataTableResponseDto<SchoolDto> toDataResponseDtoFromSchoolDto(Integer draw, Integer recordsTotal,
                                                             Integer recordsFiltered, List<SchoolDto> schools) {
        var result = new ArrayList<SchoolDto>();
        result.addAll(schools);
        return new DataTableResponseDto<>(draw, recordsTotal, recordsFiltered, result);
    }

}
