package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BaseMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntityList(D dto);

    List<D> toDtoList(E entity);
}
