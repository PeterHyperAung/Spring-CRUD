package me.minphoneaung.springcrud.students;

import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import org.springframework.web.bind.annotation.*;

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
    private PaginationResponseDto<StudentResponseDto> getStudents(
            @RequestParam(value="pageNo", defaultValue = "0") int pageNo,
           @RequestParam(value="pageSize", defaultValue = "10") int pageSize)
    {
        return service.getAllStudents(pageNo, pageSize);
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
