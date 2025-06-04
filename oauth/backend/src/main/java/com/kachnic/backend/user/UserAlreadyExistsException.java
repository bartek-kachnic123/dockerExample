package com.kachnic.backend.user;

public class UserAlreadyExistsException extends RuntimeException {
    private final static String ERROR_MESSAGE_PREFIX = "User already exists with email: ";
    public UserAlreadyExistsException(String email) {
        super(ERROR_MESSAGE_PREFIX + email);
    }
}
