package me.minphoneaung.springcrud.students;

import me.minphoneaung.springcrud.errors.EmailAlreadyExistsException;
import me.minphoneaung.springcrud.schools.SchoolRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentMapper mapper;
    private final StudentRepository repository;

    private final SchoolRepository schoolRepository;

    public StudentService(StudentMapper mapper, StudentRepository repository, SchoolRepository schoolRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.schoolRepository = schoolRepository;
    }

    public List<StudentResponseDto> getAllStudents() {
        var students = repository.findAll();
        var result = new ArrayList<StudentResponseDto>();
        for (Student student: students) {
            result.add(new StudentResponseDto(student.getId(),
                    student.getName(), student.getEmail(),
                    student.getAge(), student.getSchool(),
                    student.getSchool() == null ? null : student.getSchool().getId(),
                    student.getStartedAt()));
        }

        return result;
    }

    public StudentResponseDto getStudentById(Integer id) {
        return mapper.toStudentResponseDto(repository.findById(id).orElseThrow());
    }

    public StudentResponseDto createStudent(StudentDto dto) {
        var student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setAge(dto.age());
        student.setStartedAt(dto.startedAt());
        if(dto.schoolId() != null) {
            var school = schoolRepository.findById(dto.schoolId()).orElseThrow();
            student.setSchool(school);
        }
        try {
            return mapper.toStudentResponseDto(repository.save(student));
        } catch(DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException("Email already exists", "create-student");
        }
    }

    public StudentResponseDto updateStudentById(Integer id, StudentDto dto) {
        var student = repository.findById(id).orElseThrow();
        student.setId(id);
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setStartedAt(dto.startedAt());
        student.setAge(dto.age());
        if(dto.schoolId() != null) {
            var school = schoolRepository.findById(dto.schoolId()).orElseThrow();
            student.setSchool(school);
        } else {
            student.setSchool(null);
        }
        return mapper.toStudentResponseDto(repository.save(student));
    }

    public void deleteStudentById(Integer id) {
        repository.deleteById(id);
    }


}
