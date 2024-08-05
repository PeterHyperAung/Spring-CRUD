package me.minphoneaung.springcrud.schools;

import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import me.minphoneaung.springcrud.errors.ResourceNotFoundException;
import me.minphoneaung.springcrud.students.Student;
import me.minphoneaung.springcrud.students.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public PaginationResponseDto<SchoolDto> getAllSchools(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        var schools = repository.findAll(pageable);
        var result = new ArrayList<SchoolDto>();
        for (School school: schools) {
            result.add(new SchoolDto(school.getId(), school.getName()));
        }

        return new PaginationResponseDto<>(
                result,
                pageNo,
                pageSize,
                schools.getTotalPages(),
                result.size()
        );
    }

    public SchoolDto getSchoolById(Integer id) {
        return mapper.toSchoolDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School Not Found")));
    }

    public SchoolDto createSchool(SchoolDto dto) {
        var school = new School();
        school.setName(dto.name());
        return mapper.toSchoolDto(repository.save(school));
    }

    public SchoolDto updateSchoolById(Integer id, SchoolDto dto) {
        var school = repository.findById(id).orElseThrow();
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
