package com.kachnic.backend.user.model;

import jakarta.persistence.*;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private boolean enabled;

    private String role;

    public AppUser(String email, String password, boolean enabled, String role) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.role = "ROLE_USER";
    }

    protected AppUser() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
