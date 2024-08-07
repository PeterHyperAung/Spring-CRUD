package me.minphoneaung.springcrud.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping(path = "/schools")
public class SchoolViewController extends ViewController {

    private final SchoolService schoolService;

    @GetMapping("")
    public String schoolsList() {
        return "schools-list";
    }

    @GetMapping("/mutate/{id}")
    public String showSchoolForm(@PathVariable Integer id, Model model) {
        var school = schoolService.getSchoolById(id);
        model.addAttribute("school", school);
        return "school-form";
    }

    @PostMapping("/mutate/{id}")
    public String mutate(@Valid @ModelAttribute("school") SchoolDto data, BindingResult theBindingResults, Model model) {
        if(theBindingResults.hasErrors()) {
            model.addAttribute("schools", schoolService.getAllSchools());
            return "school-form";
        }

        if(data.id() != 0) {
            schoolService.updateSchoolById(data.id(), data);
        } else {
            schoolService.createSchool(data);
        }
        return "redirect:/schools";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        schoolService.forceDeleteSchoolById(id);
        return "redirect:/schools";
    }

}
