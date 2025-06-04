package com.kachnic.backend.auth.service;

import com.kachnic.backend.user.adapter.AppUserAdapter;
import com.kachnic.backend.user.model.AppUser;
import com.kachnic.backend.user.repository.AppUserRepository;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public AppUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AppUserAdapter(user);
    }
}
