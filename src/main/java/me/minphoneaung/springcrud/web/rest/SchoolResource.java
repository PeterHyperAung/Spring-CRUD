package me.minphoneaung.springcrud.web.rest;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesInput;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesOutput;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.service.SchoolService;
import me.minphoneaung.springcrud.web.rest.mapper.SchoolMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/schools")
@AllArgsConstructor
public class SchoolResource {


    private final SchoolService service;
    private final SchoolMapper mapper;

    @GetMapping("/hello")
    private String helloworld() {
        return "hello world";
    }

    @PostMapping
    private DataTablesOutput<SchoolDto> getSchools(
            @RequestBody DataTablesInput dataTablesInput
    ) {
        System.out.println(dataTablesInput);
        Page<SchoolDto> schools = mapper.toDtoPage(service.getAllSchools(dataTablesInput));
        return DataTablesOutput.createDataTableOutput(schools, dataTablesInput);
    }

    @GetMapping("/{id}")
    private SchoolDto getSchool(@PathVariable Integer id) {
        return service.getSchoolById(id);
    }

    @PostMapping("/school")
    private SchoolDto createSchool(@RequestBody SchoolDto dto) {
        return service.saveSchool(dto);
    }

    @PatchMapping
    private SchoolDto updateSchool(@RequestBody SchoolDto body) {
        return service.saveSchool(body);
    }

    @DeleteMapping("/{id}")
    private void deleteSchool(@PathVariable Integer id) {
        service.forceDeleteSchoolById(id);
    }

}
