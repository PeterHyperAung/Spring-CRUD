package me.minphoneaung.springcrud.security.service;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.repository.UserRepository;
import me.minphoneaung.springcrud.security.userDetails.SecurityUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    public SecurityUser loadUserById(int id) throws UsernameNotFoundException {
        return new SecurityUser(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
