package me.minphoneaung.springcrud.security.filter;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.security.mapper.UserAuthMapper;
import me.minphoneaung.springcrud.security.provider.JwtAuthentication;
import me.minphoneaung.springcrud.security.service.JwtService;
import me.minphoneaung.springcrud.security.service.JwtUserDetailsService;
import me.minphoneaung.springcrud.security.userDetails.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUserDetailsService userDetailsService;
    private JwtService jwtService;
    private UserAuthMapper mapper;

    public JwtAuthFilter(JwtUserDetailsService userDetailsService, JwtService jwtService, UserAuthMapper mapper) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.mapper = mapper;
        permitAllUrls.add("/login");
        permitAllUrls.add("/register");
        permitAllRegexUrls.add("/webjars");
    }

    private final Set<String> permitAllUrls = new HashSet<>();
    private final List<String> permitAllRegexUrls = new ArrayList<>();

    public boolean isPermitUrl(String uri) {
        for (String regexUri: permitAllRegexUrls) {
            if(uri.startsWith(regexUri)) {
                return true;
            }
        }
        return permitAllUrls.contains(uri);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var uri = request.getRequestURI();

        if(isPermitUrl(uri)) {
            filterChain.doFilter(request, response);
            return;
        }


        Cookie jwtCookie = getJwtCookie(request);

        if (jwtCookie == null) {
            response.sendRedirect("/login");
            return;
        }

        // Getting jwt and user details from cookie;
        var token = jwtCookie.getValue();
        var user = jwtService.getUser(token);
        var userId = jwtService.getUserId(token);

        if(user.getUsername() != null && jwtService.validateToken(token, userId)) {
            SecurityUser userDetails = userDetailsService.loadUserById(userId);
            // Create authentication object in context
            JwtAuthentication authenticationToken = new JwtAuthentication(
                    user.getUsername(),
                    user.getPassword(),
                    userDetails.getAuthorities(),
                    mapper.toDto(userDetails.getUser()));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
            return;
        }

        response.sendRedirect("/login");
    }

    private Cookie getJwtCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "JWT".equals(cookie.getName()))
                    .findFirst().orElse(null);
        }
        return null;
    }
}
