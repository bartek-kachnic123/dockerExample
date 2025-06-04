package com.kachnic.backend.auth.login.service;

import com.kachnic.backend.auth.dto.LoginRequestDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
    UserDetails login(LoginRequestDTO request);
}
