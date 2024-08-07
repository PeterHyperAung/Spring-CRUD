package me.minphoneaung.springcrud.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.minphoneaung.springcrud.service.SchoolService;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.service.StudentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController extends ErrorController {

    private final StudentService studentService;
    private final SchoolService schoolService;

    @GetMapping
    public String list() {
        return "students-list";
    }

    @GetMapping("/student/{id}")
    public String showStudentForm(@PathVariable(value = "id") Integer id, Model model) {
        var dto = studentService.getStudentById(id);
        model.addAttribute("student", dto);
        model.addAttribute("schools", schoolService.getAllSchools());
        return "student-form";
    }

    @PostMapping("/student")
    public String createOrUpdate(@Valid @ModelAttribute("student") StudentDto data, BindingResult bindingResult, Model model) {
        model.addAttribute("schools", schoolService.getAllSchools());

        if (bindingResult.hasErrors()) {
            return "student-form";
        }

        try {
            System.out.println(data);
            studentService.saveStudent(data);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("emailDuplicateError", "Email already exists!");
            return "student-form";
        }

        return "redirect:/students";
    }

    @PostMapping("/student/delete/{id}")
    public String delete(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}
