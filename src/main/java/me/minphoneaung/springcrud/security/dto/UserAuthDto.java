package me.minphoneaung.springcrud.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.minphoneaung.springcrud.entities.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthDto {
    private Integer id;

    @NotEmpty(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;

    private Role role;

    public UserAuthDto(Integer id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
