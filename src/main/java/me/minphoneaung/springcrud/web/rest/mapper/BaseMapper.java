package me.minphoneaung.springcrud.web.rest.mapper;

import me.minphoneaung.springcrud.web.rest.dto.SchoolDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntityList(List<D> dto);

    List<D> toDtoList(List<E> entities);

    default Page<D> toDtoPage(Page<E> entities) {
        List<D> dtoList = entities.getContent().stream()
                .map(this::toDto)
                .toList();
        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }

}
