package com.example.SpringSecurityJWT.security.jwt;

import com.example.SpringSecurityJWT.service.UserService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtils {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long jwtLifeTime;

    private final UserService userService;

    public String generateToken(String username) {
        var userDetails = userService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        var roleList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtLifeTime))
                .signWith(HS512, secret)
                .compact();
    }

    public String getUsername(String token) {
        return getToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getToken(token).get("roles", List.class);
    }


    public boolean validate(String token) {
        try {
            getToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Срок действия токена закончился: {}", e.getMessage());
        }
        return false;
    }

    private Claims getToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

}
