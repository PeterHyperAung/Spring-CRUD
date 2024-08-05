package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.dto.StudentResponseDto;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    private final SchoolRepository schoolRepository;

    public StudentMapper(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public Student toStudent(StudentDto dto) {
        var student = new Student();
        student.setName(dto.name());
        student.setAge(dto.age());
        student.setEmail(dto.email());
        var school = schoolRepository.findById(dto.schoolId()).orElseThrow();
        student.setSchool(school);
        return student;
    }

    public StudentResponseDto toStudentResponseDto(Student student) {
        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getSchool(),
                student.getSchool() == null ? null : student.getSchool().getId(),
                student.getStartedAt()
        );
    }

}
