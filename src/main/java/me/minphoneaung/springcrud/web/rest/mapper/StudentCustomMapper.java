package me.minphoneaung.springcrud.web.rest.mapper;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentCustomMapper {

    private SchoolRepository schoolRepository;

    @Named("mapSchool")
    public School mapSchool(int schoolId) {
        return schoolRepository.findById(schoolId).orElse(null);
    }
}
