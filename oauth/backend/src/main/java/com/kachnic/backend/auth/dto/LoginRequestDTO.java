package com.kachnic.backend.auth.dto;

public record LoginRequestDTO(String email, String password, String repeatPassword) {
}
