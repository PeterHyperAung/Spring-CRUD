package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.web.rest.dto.DataTableResponseDto;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper extends BaseMapper<Student, StudentDto> {

    @Mapping(source="school.id", target="schoolId")
    StudentDto toDto(Student entity);

    @Mapping(source="schoolId", target="school.id")
    Student toEntity(StudentDto entity);

}
