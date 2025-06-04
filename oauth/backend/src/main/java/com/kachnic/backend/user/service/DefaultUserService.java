package com.kachnic.backend.user.service;

import com.kachnic.backend.user.UserAlreadyExistsException;
import com.kachnic.backend.user.model.AppUser;
import com.kachnic.backend.user.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final AppUserRepository userRepository;

    public DefaultUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void save(AppUser user) {
        checkEmailIsUnique(user.getEmail());
        userRepository.save(user);
    }

    private void checkEmailIsUnique(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
    }
}
