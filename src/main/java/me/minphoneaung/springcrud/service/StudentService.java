package me.minphoneaung.springcrud.service;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesInput;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.mapper.StudentMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class StudentService {


    private final StudentRepository repository;
    private final StudentMapper mapper;

    public Page<Student> getAllStudents(DataTablesInput dataTablesInput) {
        Pageable pageable = dataTablesInput.getPageable();
        var searchValue = dataTablesInput.getSearch().getValue();
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSchool_NameContainingIgnoreCase
                (searchValue, searchValue, searchValue, pageable);
    }

    public StudentDto getStudentById(Integer id) {
        return mapper.toDto(repository.findById(id).orElse(
                new Student(0, "", "", null, null, LocalDate.now())
        ));
    }

    public StudentDto saveStudent(StudentDto dto) throws DataIntegrityViolationException {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void deleteStudentById(Integer id) {
        repository.deleteById(id);
    }

}
