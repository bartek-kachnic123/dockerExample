package com.kachnic.backend.config;

import com.kachnic.backend.auth.manager.AccessJwtManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class JwtConfiguration {

    @Bean
    public AccessJwtManager jwtAccessManager(@Value("${jwt.secret}") String jwtSecret,
                                         @Value("${jwt.expiration}") long jwtExpirationMs) {
        return new AccessJwtManager(jwtSecret, Duration.ofMillis(jwtExpirationMs));
    }
}
