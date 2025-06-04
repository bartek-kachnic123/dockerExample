package com.kachnic.backend.auth.register.controller;

import com.kachnic.backend.auth.dto.RegistrationRequestDTO;
import com.kachnic.backend.auth.register.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegistrationRequestDTO request) {
        registerService.registerUser(request);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
