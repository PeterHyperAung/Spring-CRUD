package me.minphoneaung.springcrud.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import me.minphoneaung.springcrud.entities.Role;
import me.minphoneaung.springcrud.security.dto.UserAuthDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
@NoArgsConstructor
public class JwtService {
    private final String SECRET_KEY = "secret";
    public static final Integer JWT_EXPIRATION = 1000 * 60 * 60 * 24;

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UserAuthDto getUser(String token) {
        var claims = extractAllClaims(token);
        return new UserAuthDto(claims.get("id", Integer.class),
                claims.get("username", String.class),
                Role.valueOf(claims.get("role", String.class))
        );
    }

    public Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public String generateToken(String username, UserAuthDto dto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", dto.getId());
        claims.put("username", dto.getUsername());
        claims.put("role", dto.getRole());
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, String username) {
        final String name = getUsername(token);
        return (name.equals(username) && !isTokenExpired(token));
    }

}
