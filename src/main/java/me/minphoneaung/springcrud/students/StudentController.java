package me.minphoneaung.springcrud.students;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/students")
public class StudentController {


    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    private String helloworld() {
        return "hello world";
    }

    @GetMapping
    private List<StudentResponseDto> getSchools() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    private StudentResponseDto getSchool(@PathVariable Integer id) {
        return service.getStudentById(id);
    }

    @PostMapping
    private StudentResponseDto getSchool(@RequestBody StudentDto dto) {
        return service.createStudent(dto);
    }

    @PatchMapping("/{id}")
    private StudentResponseDto updateSchool(@PathVariable Integer id, @RequestBody StudentDto body) {
        return service.updateStudentById(id, body);
    }

    @DeleteMapping("{id}")
    private void deleteSchool(@PathVariable Integer id) {
        service.deleteStudentById(id);
    }



}
