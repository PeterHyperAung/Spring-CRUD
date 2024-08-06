package me.minphoneaung.springcrud.service;

import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.errors.EmailAlreadyExistsException;
import me.minphoneaung.springcrud.errors.ResourceNotFoundException;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.mapper.StudentMapper;
import me.minphoneaung.springcrud.web.rest.dto.StudentResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
                    student.getDateOfBirth(), student.getSchool(),
                    student.getSchool() == null ? null : student.getSchool().getId(),
                    student.getStartedAt()));
        }

        return result;
    }

    public List<StudentResponseDto> getAllSchools(int start, int length, String searchValue, int column, String direction) {
        Sort sort = Sort.by(Sort.Order.by(getSortColumn(column)).with(Sort.Direction.fromString(direction)));
        PageRequest pageRequest = PageRequest.of(start / length, length, sort);
        var students = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSchool_NameContainingIgnoreCase
                (searchValue, searchValue, searchValue, pageRequest);
        var result = new ArrayList<StudentResponseDto>();
        for (Student student: students) {
            result.add(mapper.toStudentResponseDto(student));
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
                return "age";
            case 4:
                return "school.name";
            case 5:
                return "startedAt";
        };
        return "name";
    }

    public PaginationResponseDto<StudentResponseDto> getAllStudents(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var students = repository.findAll(pageable);
        ArrayList<StudentResponseDto> result = new ArrayList<>();
        for (Student student: students) {
            result.add(new StudentResponseDto(student.getId(),
                    student.getName(), student.getEmail(),
                    student.getDateOfBirth(), student.getSchool(),
                    student.getSchool() == null ? null : student.getSchool().getId(),
                    student.getStartedAt()));
        }

        return new PaginationResponseDto<>(
                result,
                pageNo,
                pageSize,
                students.getTotalPages(),
                result.size()
        );
    }

    public StudentResponseDto getStudentById(Integer id) {
        return mapper.toStudentResponseDto(repository.findById(id).orElse(null));
    }

    public StudentResponseDto createStudent(StudentDto dto) throws DataIntegrityViolationException {
        var student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setDateOfBirth(dto.dateOfBirth());
        student.setStartedAt(dto.startedAt());
        if(dto.schoolId() != null) {
            var school = schoolRepository.findById(dto.schoolId()).orElse(null);
            student.setSchool(school);
        }

        return mapper.toStudentResponseDto(repository.save(student));
    }

    public StudentResponseDto updateStudentById(Integer id, StudentDto dto) throws DataIntegrityViolationException {
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

        return mapper.toStudentResponseDto(repository.save(student));
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
