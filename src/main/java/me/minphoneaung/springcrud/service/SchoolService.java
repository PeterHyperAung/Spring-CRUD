package me.minphoneaung.springcrud.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesInput;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.web.rest.mapper.SchoolMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolMapper mapper;
    private final SchoolRepository repository;
    private final StudentRepository studentRepository;

    public List<SchoolDto> getAllSchools() {
        return mapper.toDtoList(repository.findAll());
    }
    public Page<School> getAllSchools(DataTablesInput dataTablesInput) {
        PageRequest pageRequest = dataTablesInput.getPageable();
        var searchValue = dataTablesInput.getSearch().getValue();
        return repository.findByNameContainingIgnoreCaseOrPrincipalContainingIgnoreCase
                (searchValue, searchValue, pageRequest);
    }


    public SchoolDto getSchoolById(Integer id) {
        var school = new School();
        school.setId(0);
        return mapper.toDto(repository.findById(id)
                .orElse(school));
    }

    public SchoolDto saveSchool(SchoolDto dto) {
        return mapper.toDto(
                repository.save(mapper.toEntity(dto))
        );
    }

    @Transactional
    public void forceDeleteSchoolById(Integer id) {
        List<Student> students = studentRepository.findBySchoolId(id);
        for(Student student: students) {
            student.setSchool(null);
            studentRepository.save(student);
        }
        repository.deleteById(id);
    }


}
