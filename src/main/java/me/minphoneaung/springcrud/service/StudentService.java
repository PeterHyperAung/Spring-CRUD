package me.minphoneaung.springcrud.service;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.errors.ResourceNotFoundException;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.mapper.StudentMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {


    private final StudentRepository repository;
    private final SchoolRepository schoolRepository;
    private final StudentMapper mapper;

    public List<StudentDto> getAllStudents() {
        return mapper.toDtoList(repository.findAll());
    }

    public List<StudentDto> getAllSchools(int start, int length, String searchValue, int column, String direction) {
        Sort sort = Sort.by(Sort.Order.by(getSortColumn(column)).with(Sort.Direction.fromString(direction)));
        PageRequest pageRequest = PageRequest.of(start / length, length, sort);
        var students = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSchool_NameContainingIgnoreCase
                (searchValue, searchValue, searchValue, pageRequest);
        var result = new ArrayList<StudentDto>();
        for (Student student: students) {
            result.add(mapper.toDto(student));
        }
        return result;
    }

    public String getSortColumn(int column) {
        switch(column) {
            case 0:
                return "id";
            case 1:
                return "name";
            case 2:
                return "email";
            case 3:
                return "dateOfBirth";
            case 4:
                return "school.name";
            case 5:
                return "startedAt";
        };
        return "name";
    }

    public PaginationResponseDto<StudentDto> getAllStudents(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var students = repository.findAll(pageable);
        ArrayList<StudentDto> result = new ArrayList<>();
        for (Student student: students) {
            result.add(mapper.toDto(student));
        }

        return new PaginationResponseDto<>(
                result,
                pageNo,
                pageSize,
                students.getTotalPages(),
                result.size()
        );
    }

    public StudentDto getStudentById(Integer id) {
        return mapper.toDto(repository.findById(id).orElse(
                new Student(0, "", "", LocalDate.now(), null, LocalDate.now())
        ));
    }

    public StudentDto createStudent(StudentDto dto) throws DataIntegrityViolationException {
        var student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setDateOfBirth(dto.dateOfBirth());
        student.setStartedAt(dto.startedAt());
        if(dto.schoolId() != null) {
            var school = schoolRepository.findById(dto.schoolId()).orElse(null);
            student.setSchool(school);
        }

        return mapper.toDto(repository.save(student));
    }

    public StudentDto updateStudentById(Integer id, StudentDto dto) throws DataIntegrityViolationException {
        var student = repository.findById(id).orElseThrow();
        student.setId(id);
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setStartedAt(dto.startedAt());
        student.setDateOfBirth(dto.dateOfBirth());
        if(dto.schoolId() != null) {
            var school = schoolRepository.findById(dto.schoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
            student.setSchool(school);
        } else {
            student.setSchool(null);
        }

        return mapper.toDto(repository.save(student));
    }

    public void deleteStudentById(Integer id) {
        repository.deleteById(id);
    }

    public long countAllStudent() {
        return repository.count();
    }

    public long countFilteredStudent(String searchTerm) {
        return repository.countByNameContainingIgnoreCase(searchTerm);
    }

}
