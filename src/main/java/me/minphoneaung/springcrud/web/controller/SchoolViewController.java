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
        return "school";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("school", new SchoolDto(null, "", ""));
        return "create-school";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("school") SchoolDto data, BindingResult theBindingResults, Model model) {
        if(theBindingResults.hasErrors()) {
            model.addAttribute("schools", schoolService.getAllSchools());
            return "create-school";
        }
        schoolService.createSchool(data);
        return "redirect:/schools";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        model.addAttribute("school", schoolService.getSchoolById(id));
        return "edit-school";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute SchoolDto data) {
        schoolService.updateSchoolById(id, data);
        return "redirect:/schools";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        schoolService.deleteSchoolById(id);
        return "redirect:/schools";
    }

}
