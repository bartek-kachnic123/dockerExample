package com.kachnic.backend.auth.register.service;

import com.kachnic.backend.auth.dto.RegistrationRequestDTO;

public interface RegisterService {
    void registerUser(RegistrationRequestDTO request);
}
