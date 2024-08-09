package me.minphoneaung.springcrud.web.controller;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.User;
import me.minphoneaung.springcrud.repository.UserRepository;
import me.minphoneaung.springcrud.security.dto.AuthRequestDto;
import me.minphoneaung.springcrud.security.dto.UserAuthDto;
import me.minphoneaung.springcrud.security.mapper.UserAuthMapper;
import me.minphoneaung.springcrud.security.service.JwtAuthentication;
import me.minphoneaung.springcrud.security.service.JwtService;
import me.minphoneaung.springcrud.security.service.JwtUserDetailsService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class AuthController {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private UserAuthMapper userAuthMapper;
    private JwtUserDetailsService userDetailsService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthRequestDto authRequestDTO, HttpServletResponse response) {
        var user = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new JwtAuthentication(
                        authRequestDTO.getUsername(),
                        authRequestDTO.getPassword(),
                        user.getAuthorities(),
                        new UserAuthDto(user.getUser().getId(),
                                authRequestDTO.getUsername(),
                                authRequestDTO.getPassword(),
                                user.getUser().getRole())
                ));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUser().getUsername(),
                    userAuthMapper.toDto(user.getUser()));

            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("JWT", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(JwtService.JWT_EXPIRATION)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            response.addHeader(HttpHeaders.AUTHORIZATION, token);

            return "home";
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleToUser();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/register?usernameAlreadyExists";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", null);

        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/login";
    }
}
