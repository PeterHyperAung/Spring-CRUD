package me.minphoneaung.springcrud.service;

import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.errors.ResourceNotFoundException;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.web.rest.mapper.SchoolMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolService {

    private final SchoolMapper mapper;
    private final SchoolRepository repository;

    private final StudentRepository studentRepository;

    public SchoolService(SchoolMapper mapper, SchoolRepository repository, StudentRepository studentRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    public List<SchoolDto> getAllSchools() {
        var schools = repository.findAll();
        var result = new ArrayList<SchoolDto>();
        for (School school: schools) {
            result.add(new SchoolDto(school.getId(), school.getName()));
        }

        return result;
    }

    public List<SchoolDto> getAllSchools(int start, int length, String searchValue, int column, String direction) {
        Sort sort = Sort.by(Sort.Order.by(getSortColumn(column)).with(Sort.Direction.fromString(direction)));
        PageRequest pageRequest = PageRequest.of(start / length, length, sort);
        var schools = repository.findByNameContainingIgnoreCase(searchValue, pageRequest);
        var result = new ArrayList<SchoolDto>();
        for (School school: schools) {
            result.add(new SchoolDto(school.getId(), school.getName()));
        }
        return result;
    }

    private String getSortColumn(int column) {
        switch(column) {
            case 0:
                return "id";
            case 1:
                return "name";
        };
        return "name";
    }

    public SchoolDto getSchoolById(Integer id) {
        return mapper.toSchoolDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Not Found")));
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
        return mapper.toSchoolDto(repository.save(school));
    }

    public SchoolDto updateSchoolById(Integer id, SchoolDto dto) {
        var school = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Not Found"));
        school.setName(dto.name());
        return mapper.toSchoolDto(repository.save(school));
    }

    public void deleteSchoolById(Integer id) {
        List<Student> students = studentRepository.findBySchoolId(id);
        for(Student student: students) {
            student.setSchool(null);
            studentRepository.save(student);
        }
        repository.deleteById(id);
    }


}
