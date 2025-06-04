package com.kachnic.backend.auth.dto;

public record RegistrationRequestDTO(String email, String password, String confirmPassword) {
}
