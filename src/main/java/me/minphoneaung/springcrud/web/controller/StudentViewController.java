package me.minphoneaung.springcrud.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.minphoneaung.springcrud.service.SchoolService;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import me.minphoneaung.springcrud.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class StudentViewController extends ViewController {

    private final StudentService studentService;
    private final SchoolService schoolService;

    @GetMapping
    public String list(Model model,
           @RequestParam(value="pageNo", defaultValue = "0") int pageNo,
           @RequestParam(value="pageSize", defaultValue = "10") int pageSize)
    {
        pageNo = pageNo < 0 ? 0: pageNo;
        var students = studentService.getAllStudents(pageNo, pageSize);
        addPaginationAttribute(model, students, "students");
        return "home";
    }

    @GetMapping("/students/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new StudentDto("", "", null, null, null));
        model.addAttribute("schools", schoolService.getAllSchools());
        return "create-student";
    }

    @PostMapping("/students/create")
    public String create(@Valid @ModelAttribute("student") StudentDto data, BindingResult theBindingResult, Model model){
        if(theBindingResult.hasErrors()) {
            model.addAttribute("schools", schoolService.getAllSchools());
            return "create-student";
        }
        studentService.createStudent(data);
        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("schools", schoolService.getAllSchools());
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit-student";
    }

    @PostMapping("/students/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute StudentDto data) {
        studentService.updateStudentById(id, data);
        return "redirect:/";
    }

    @PostMapping("/students/delete/{id}")
    public String delete(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }


}
