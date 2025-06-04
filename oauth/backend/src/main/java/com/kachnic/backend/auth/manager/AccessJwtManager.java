package com.kachnic.backend.auth.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.util.Date;


public class AccessJwtManager extends AbstractJwtManager {
    private static final String BEARER_PREFIX = "Bearer ";

    public AccessJwtManager(String secret, Duration expiration) {
        super(secret, expiration);
    }

    @Override
    public JwtType getType() {
        return JwtType.ACCESS;
    }

    @Override
    public boolean isExpired(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    @Override
    public String parse(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuth != null && headerAuth.startsWith(BEARER_PREFIX)) {
            return headerAuth.substring(BEARER_PREFIX.length());
        }
        return "";
    }

    @Override
    public void store(String token, HttpHeaders headers) {
        headers.add(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);
    }
}
