package me.minphoneaung.springcrud.views;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import me.minphoneaung.springcrud.schools.SchoolDto;
import me.minphoneaung.springcrud.schools.SchoolService;
import me.minphoneaung.springcrud.students.StudentDto;
import me.minphoneaung.springcrud.students.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    private StudentService studentService;
    private SchoolService schoolService;

    public ViewController(StudentService studentService, SchoolService schoolService) {
        this.studentService = studentService;
        this.schoolService = schoolService;
    }


    @GetMapping
    public String homePage(Model model) {
        var students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "home";
    }

    @GetMapping("/students/create")
    public String createStudentPage(Model model) {
        model.addAttribute("student", new StudentDto("", "", null, null, null));
        model.addAttribute("schools", schoolService.getAllSchools());
        return "create-student";
    }

    @PostMapping("/students/create")
    public String handleStudentCreate(@Valid @ModelAttribute("student") StudentDto data, BindingResult theBindingResult, Model model){
        if(theBindingResult.hasErrors()) {
            model.addAttribute("schools", schoolService.getAllSchools());
            return "create-student";
        }
        studentService.createStudent(data);
        return "redirect:/";
    }

    @GetMapping("/students/edit/{id}")
    public String studentEditPage(@PathVariable Integer id, Model model) {
        model.addAttribute("schools", schoolService.getAllSchools());
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit-student";
    }

    @PostMapping("/students/edit/{id}")
    public String handleStudentUpdate(@PathVariable Integer id, @ModelAttribute StudentDto data) {
        studentService.updateStudentById(id, data);
        return "redirect:/";
    }

    @PostMapping("/students/delete/{id}")
    public String handleStudentDelete(@PathVariable Integer id) {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }

    @GetMapping("/schools")
    public String schoolListPage(Model model) {
        model.addAttribute("schools", schoolService.getAllSchools());
        return "school";
    }


    @GetMapping("/schools/create")
    public String handleSchoolCreate(Model model) {
        model.addAttribute("school", new SchoolDto(null, ""));
        return "create-school";
    }

    @PostMapping("/schools/create")
    public String handleSchoolCreate(@Valid @ModelAttribute("school") SchoolDto data, BindingResult theBindingResults, Model model) {
        if(theBindingResults.hasErrors()) {
            model.addAttribute("schools", schoolService.getAllSchools());
            return "create-school";
        }
        schoolService.createSchool(data);
        return "redirect:/schools";
    }

    @GetMapping("/schools/edit/{id}")
    public String schoolEditPage(@PathVariable Integer id, Model model) {
        model.addAttribute("school", schoolService.getSchoolById(id));
        return "edit-school";
    }

    @PostMapping("/schools/edit/{id}")
    public String schoolHandleUpdate(@PathVariable Integer id, @ModelAttribute SchoolDto data) {
        schoolService.updateSchoolById(id, data);
        return "redirect:/schools";
    }

    @PostMapping("/schools/delete/{id}")
    public String handleSchoolDelete(@PathVariable Integer id) {
        schoolService.deleteSchoolById(id);
        return "redirect:/schools";
    }

    @GetMapping("/error")
    public String errorHandler(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println("STATUS");
        System.out.println(status);
        if(status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == 404) {
                return "error/404";
            }

        }

        return "home";
    }


}
