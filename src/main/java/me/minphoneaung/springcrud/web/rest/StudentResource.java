package me.minphoneaung.springcrud.web.rest;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.service.StudentService;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesInput;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesOutput;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.web.rest.mapper.StudentMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


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

    @PostMapping
    private DataTablesOutput<StudentDto> getStudents(
            @RequestBody DataTablesInput dataTablesInput
    )
    {
        System.out.println("Hello");
        System.out.println(dataTablesInput);
        Page<StudentDto> students = mapper.toDtoPage(service.getAllStudents(dataTablesInput));
        return DataTablesOutput.createDataTableOutput(students, dataTablesInput);
    }

    @GetMapping("/{id}")
    private StudentDto getStudent(@PathVariable Integer id) {
        return service.getStudentById(id);
    }

    @PostMapping("/student")
    private StudentDto createStudent(@RequestBody StudentDto dto) {
        return service.saveStudent(dto);
    }

    @PatchMapping("/{id}")
    private StudentDto updateStudent(@PathVariable Integer id, @RequestBody StudentDto body) {
        return service.saveStudent(body);
    }

    @DeleteMapping("{id}")
    private void deleteStudent(@PathVariable Integer id) {
        service.deleteStudentById(id);
    }


}
