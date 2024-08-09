package me.minphoneaung.springcrud.security.service;

import me.minphoneaung.springcrud.security.dto.UserAuthDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {

    private UserAuthDto userAuthDto;

    public JwtAuthentication(Object principal,
                             Object credentials,
                             Collection<? extends GrantedAuthority> authorities,
                             UserAuthDto userAuthDto) {
        super(principal, credentials, authorities);
        this.userAuthDto = userAuthDto;
    }

    public JwtAuthentication(Object principal,
                             Object credentials,
                             UserAuthDto userAuthDto) {
        super(principal, credentials);
        this.userAuthDto = userAuthDto;
    }

    public UserAuthDto getUserAuthDto() {
        return userAuthDto;
    }

    public UserAuthDto getUserDto() {
        return (UserAuthDto) this.getPrincipal();
    }

}
