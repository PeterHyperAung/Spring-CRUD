package me.minphoneaung.springcrud.security.mapper;


import me.minphoneaung.springcrud.entities.User;
import me.minphoneaung.springcrud.security.dto.UserAuthDto;
import me.minphoneaung.springcrud.web.rest.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface UserAuthMapper extends BaseMapper<User, UserAuthDto> {
}
