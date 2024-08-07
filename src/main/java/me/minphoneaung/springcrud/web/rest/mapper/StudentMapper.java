package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = StudentCustomMapper.class)
public interface StudentMapper extends BaseMapper<Student, StudentDto> {

    @Mapping(source="school.id", target="schoolId")
    StudentDto toDto(Student entity);

    @Mapping(source="schoolId", target="school", qualifiedByName = "mapSchool")
    Student toEntity(StudentDto entity);

}
