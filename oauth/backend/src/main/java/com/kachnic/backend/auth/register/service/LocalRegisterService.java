package com.kachnic.backend.auth.register.service;

import com.kachnic.backend.auth.dto.RegistrationRequestDTO;
import com.kachnic.backend.user.model.AppUser;
import com.kachnic.backend.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LocalRegisterService implements RegisterService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LocalRegisterService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationRequestDTO request) {
        userService.save(new AppUser(
                request.email(),
                passwordEncoder.encode(request.password())
        ));
    }
}
