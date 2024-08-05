package me.minphoneaung.springcrud.web.rest;

import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import me.minphoneaung.springcrud.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/schools")
public class SchoolResource {


    private final SchoolService service;

    public SchoolResource(SchoolService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    private String helloworld() {
        return "hello world";
    }

    @GetMapping("")
    public List<SchoolDto> getUsersData() {
        return service.getAllSchools();
    }

    @GetMapping("/server-side-pagination")
    private Map<String, Object> getSchools(
            @RequestParam("draw") int draw,
            @RequestParam("start") int start,
            @RequestParam("length") int length,
            @RequestParam(value = "search[value]", defaultValue = "") String searchValue,
            @RequestParam(value = "order[0][column]", defaultValue = "0") int column,
            @RequestParam(value = "order[0][dir]", defaultValue = "asc") String direction
    ) {
        Map<String, Object> response = new HashMap<>();

        // Total number of records in the database
        int totalRecords = service.countAllSchools();

        // Fetch filtered, paginated, and sorted data
        List<SchoolDto> schools = service.getAllSchools(start, length, searchValue, column, direction);

        // Prepare response
        response.put("draw", draw);
        response.put("recordsTotal", totalRecords);
        response.put("recordsFiltered", totalRecords); // Update this if you have a separate count for filtered records
        response.put("data", schools);

        return response;
    }

    @GetMapping("/{id}")
    private SchoolDto getSchool(@PathVariable Integer id) {
        return service.getSchoolById(id);
    }

    @PostMapping
    private SchoolDto getSchool(@RequestBody SchoolDto dto) {
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
