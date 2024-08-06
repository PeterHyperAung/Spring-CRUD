package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.web.rest.dto.DataTableResponseDto;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.dto.StudentResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentMapper {

    private final SchoolRepository schoolRepository;

    public StudentMapper(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public Student toStudent(StudentDto dto) {
        var student = new Student();
        student.setId(dto.id());
        student.setName(dto.name());
        student.setDateOfBirth(dto.dateOfBirth());
        student.setEmail(dto.email());
        var school = schoolRepository.findById(dto.schoolId()).orElseThrow();
        student.setSchool(school);
        return student;
    }

    public StudentResponseDto toStudentResponseDto(Student student) {
        if(student == null) return null;
        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getSchool(),
                student.getSchool() == null ? null : student.getSchool().getId(),
                student.getStartedAt()
        );
    }

    public DataTableResponseDto<StudentResponseDto> toDataResponseDtoFromStudent
            (Integer draw, Integer recordsTotal, Integer recordsFiltered, List<Student> students) {
        var result = new ArrayList<StudentResponseDto>();
        for (Student student: students) {
            result.add(toStudentResponseDto(student));
        }
        return new DataTableResponseDto<>(draw, recordsTotal, recordsFiltered, result);
    }


    public DataTableResponseDto<StudentResponseDto> toDataResponseDtoFromStudentResponseDto
            (Integer draw, long recordsTotal, long recordsFiltered, List<StudentResponseDto> students) {
        var result = new ArrayList<StudentResponseDto>();
        result.addAll(students);
        return new DataTableResponseDto<>(draw, recordsTotal, recordsFiltered, result);
    }

}
