package com.kachnic.backend.auth;

import com.kachnic.backend.auth.manager.AccessJwtManager;
import com.kachnic.backend.auth.manager.JwtManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class AuthJwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtManager jwtManager;

    public AuthJwtFilter(UserDetailsService userDetailsService, AccessJwtManager accessJwtManager) {
        this.userDetailsService = userDetailsService;
        this.jwtManager = accessJwtManager;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {

            String jwt = jwtManager.parse(request);

            if (jwtManager.validate(jwt)) {
                String email = jwtManager.getClaims(jwt).getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UsernameNotFoundException ignored) { }
        filterChain.doFilter(request, response);
    }
}
