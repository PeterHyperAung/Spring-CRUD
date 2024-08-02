package me.minphoneaung.springcrud.schools;

import me.minphoneaung.springcrud.students.Student;
import me.minphoneaung.springcrud.students.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public SchoolDto getSchoolById(Integer id) {
        return mapper.toSchoolDto(repository.findById(id).orElseThrow());
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
