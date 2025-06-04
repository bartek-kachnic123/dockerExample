package com.kachnic.backend.auth.manager;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

public abstract class AbstractJwtManager implements JwtManager {
    private final String secret;
    protected final Duration expiration;
    protected SecretKey key;

    public AbstractJwtManager(String secret, Duration expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(String email) {
        Date now = new Date();
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration.toMillis()))
                .signWith(key)
                .compact();
    }


    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validate(String token) {

        try {
            getClaims(token);
            return true;
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
        return false;
    }
    public abstract JwtType getType();
    public abstract String parse(HttpServletRequest request);
    public abstract void store(String token, HttpHeaders headers);

    public void generateAndStore(UserDetails userDetails, HttpHeaders headers) {
        String token = generate(userDetails.getUsername());
        store(token, headers);
    }
}

