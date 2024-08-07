package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BaseMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntityList(List<D> dto);

    List<D> toDtoList(List<E> entity);
}
