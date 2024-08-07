package me.minphoneaung.springcrud.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.errors.ResourceNotFoundException;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.web.rest.mapper.SchoolMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolMapper mapper;
    private final SchoolRepository repository;
    private final StudentRepository studentRepository;

    public List<SchoolDto> getAllSchools() {
        var schools = repository.findAll();
        return mapper.toDtoList(schools);
    }

    public List<SchoolDto> getAllSchools(int start, int length, String searchValue, int column, String direction) {
        Sort sort = Sort.by(Sort.Order.by(getSortColumn(column)).with(Sort.Direction.fromString(direction)));
        PageRequest pageRequest = PageRequest.of(start / length, length, sort);
        var schools = repository.findByNameContainingIgnoreCaseOrPrincipalContainingIgnoreCase(searchValue, searchValue, pageRequest);
        return mapper.toDtoList(schools);
    }

    private String getSortColumn(int column) {
        switch(column) {
            case 0:
                return "id";
            case 1:
                return "name";
            case 2:
                return "principal";
        };
        return "name";
    }

    public SchoolDto getSchoolById(Integer id) {
        var school = new School();
        school.setId(0);
        return mapper.toDto(repository.findById(id)
                .orElse(school));
    }

    public int countAllSchools() {
        return (int) repository.count();
    }

    public int countFilteredSchools(String searchValue) {
        return (int) repository.countByNameContainingIgnoreCase(searchValue);
    }

    public SchoolDto createSchool(SchoolDto dto) {
        var school = new School();
        school.setName(dto.name());
        school.setPrincipal(dto.principal());
        return mapper.toDto(repository.save(school));
    }

    public SchoolDto updateSchoolById(Integer id, SchoolDto dto) {
        var school = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Not Found"));
        school.setName(dto.name());
        school.setPrincipal(dto.principal());
        return mapper.toDto(repository.save(school));
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
