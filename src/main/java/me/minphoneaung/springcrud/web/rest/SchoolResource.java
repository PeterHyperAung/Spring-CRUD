package me.minphoneaung.springcrud.web.rest;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesInput;
import me.minphoneaung.springcrud.web.rest.dto.DataTablesOutput;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.service.SchoolService;
import me.minphoneaung.springcrud.web.rest.mapper.SchoolMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return DataTablesOutput.createDataTableOutput(
                mapper.toDtoPage(service.getAllSchools(dataTablesInput)),
                dataTablesInput);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    private SchoolDto getSchool(@PathVariable Integer id) {
        return service.getSchoolById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/school")
    private SchoolDto createSchool(@RequestBody SchoolDto dto) {
        return service.saveSchool(dto);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping
    private SchoolDto updateSchool(@RequestBody SchoolDto body) {
        return service.saveSchool(body);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    private void deleteSchool(@PathVariable Integer id) {
        service.forceDeleteSchoolById(id);
    }

}
