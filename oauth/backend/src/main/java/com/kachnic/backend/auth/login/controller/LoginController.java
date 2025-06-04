package com.kachnic.backend.auth.login.controller;

import com.kachnic.backend.auth.dto.LoginRequestDTO;
import com.kachnic.backend.auth.login.service.LoginService;
import com.kachnic.backend.auth.manager.JwtManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;
    private final JwtManager jwtManager;

    public LoginController(LoginService loginService,
                           JwtManager jwtManager) {
        this.loginService = loginService;
        this.jwtManager = jwtManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        UserDetails userDetails = loginService.login(loginRequestDTO);

        HttpHeaders headers = new HttpHeaders();
        jwtManager.generateAndStore(userDetails, headers);

        return ResponseEntity.ok().
                headers(headers).build();
    }

    @GetMapping("/user")
    public ResponseEntity<String> currentUser(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
