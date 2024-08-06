package me.minphoneaung.springcrud.web.rest;

import me.minphoneaung.springcrud.web.rest.dto.DataTableResponseDto;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.service.SchoolService;
import me.minphoneaung.springcrud.web.rest.mapper.SchoolMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/schools")
public class SchoolResource {


    private final SchoolService service;
    private final SchoolMapper mapper;

    public SchoolResource(SchoolService service, SchoolMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/hello")
    private String helloworld() {
        return "hello world";
    }

    @GetMapping("")
    private DataTableResponseDto<SchoolDto> getSchools(
            @RequestParam("draw") int draw,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam(value = "search[value]", defaultValue = "") String searchValue,
            @RequestParam(value = "order[0][column]", defaultValue = "0") int column,
            @RequestParam(value = "order[0][dir]", defaultValue = "asc") String direction
    ) {
        int totalRecords = service.countAllSchools();
        int filteredRecords = service.countFilteredSchools(searchValue);

        List<SchoolDto> schools = service.getAllSchools(start, length, searchValue, column, direction);

        return mapper.toDataResponseDtoFromSchoolDto(draw, totalRecords, filteredRecords, schools);
    }

    @GetMapping("/{id}")
    private SchoolDto getSchool(@PathVariable Integer id) {
        return service.getSchoolById(id);
    }

    @PostMapping
    private SchoolDto createSchool(@RequestBody SchoolDto dto) {
        return service.createSchool(dto);
    }

    @PatchMapping("/{id}")
    private SchoolDto updateSchool(@PathVariable Integer id, @RequestBody SchoolDto body) {
        return service.updateSchoolById(id, body);
    }

    @DeleteMapping("{id}")
    private void deleteSchool(@PathVariable Integer id) {
        service.deleteSchoolById(id);
    }

}
