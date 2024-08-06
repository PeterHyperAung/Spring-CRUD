package me.minphoneaung.springcrud.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.service.SchoolService;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.service.StudentService;
import me.minphoneaung.springcrud.web.rest.mapper.StudentMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Controller
public class StudentViewController extends ViewController {

    private final StudentService studentService;
    private final SchoolService schoolService;
    private final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping
    public String list() {
        return "home";
    }

    @GetMapping("/students/mutate/{id}")
    public String showStudentForm(@PathVariable(value = "id") Integer id, Model model) {
       var student = studentService.getStudentById(id);
        StudentDto dto;
        if(student == null) {
            dto = new StudentDto(0, "", "", null, null, null);
        } else {
            dto = new StudentDto(student.id(), student.name(),
                    student.email(), student.dateOfBirth(),
                    student.schoolId(), student.startedAt());
        }

        model.addAttribute("student", dto);
        model.addAttribute("schools", schoolService.getAllSchools());
        return "student-form";
    }

    @PostMapping("/students/mutate/{id}")
    public String mutate(@Valid @ModelAttribute("student") StudentDto data, BindingResult bindingResult, Model model) {
        model.addAttribute("schools", schoolService.getAllSchools());

        if (bindingResult.hasErrors()) {
            return "student-form";
        }

        try {
            var student = studentService.getStudentById(data.id());
            if(student == null) {
                studentService.createStudent(data);
                model.addAttribute("success", "Student created");
            } else {
                studentService.updateStudentById(data.id(), data);
                model.addAttribute("success", "Student updated");
            }
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("emailDuplicateError", "Email already exists!");
            return "student-form";
        }

        return "redirect:/";
    }

    @PostMapping("/students/delete/{id}")
    public String delete(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }


}
