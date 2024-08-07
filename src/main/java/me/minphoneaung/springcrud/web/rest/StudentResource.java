package me.minphoneaung.springcrud.web.rest;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.service.StudentService;
import me.minphoneaung.springcrud.web.rest.dto.DataTableResponseDto;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.mapper.StudentMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/students")
@AllArgsConstructor
public class StudentResource {


    private final StudentService service;
    private final StudentMapper mapper;


    @GetMapping("/hello")
    private String helloworld() {
        return "hello world";
    }

    @GetMapping
    private DataTableResponseDto<StudentDto> getStudents(
            @RequestParam(value="draw", defaultValue = "0") int draw,
            @RequestParam(value="start", defaultValue = "0") int start,
            @RequestParam(value="length", defaultValue = "10") int length,
            @RequestParam(value = "search[value]", defaultValue = "") String searchValue,
            @RequestParam(value = "order[0][column]", defaultValue = "0") int column,
            @RequestParam(value = "order[0][dir]", defaultValue = "asc") String direction
    )
    {
        long totalRecords = service.countAllStudent();
        long filteredRecords = service.countFilteredStudent(searchValue);

        List<StudentDto> students = service.getAllSchools(start, length, searchValue, column, direction);

        return toDataTableResponseDtoFromDto(draw, totalRecords, filteredRecords, students);
    }

    @GetMapping("/{id}")
    private StudentDto getStudent(@PathVariable Integer id) {
        return service.getStudentById(id);
    }

    @PostMapping
    private StudentDto createStudent(@RequestBody StudentDto dto) {
        return service.createStudent(dto);
    }

    @PatchMapping("/{id}")
    private StudentDto updateStudent(@PathVariable Integer id, @RequestBody StudentDto body) {
        return service.updateStudentById(id, body);
    }

    @DeleteMapping("{id}")
    private void deleteStudent(@PathVariable Integer id) {
        service.deleteStudentById(id);
    }


    private DataTableResponseDto<StudentDto> toDataTableResponseDtoFromDto(
            Integer draw, long recordsTotal, long recordsFiltered, List<StudentDto> students
    ) {
        return new DataTableResponseDto<>(draw, recordsTotal, recordsFiltered, students);
    }

}
