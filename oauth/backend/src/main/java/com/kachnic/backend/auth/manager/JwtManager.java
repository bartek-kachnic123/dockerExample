package com.kachnic.backend.auth.manager;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtManager {

    String generate(String email);
    Claims getClaims(String token);
    boolean isExpired(String token);
    boolean validate(String token);
    JwtType getType();
    String parse(HttpServletRequest request);
    void store(String token, HttpHeaders headers);
    void generateAndStore(UserDetails userDetails, HttpHeaders headers);
}
