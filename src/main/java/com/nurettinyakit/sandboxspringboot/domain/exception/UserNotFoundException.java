package com.nurettinyakit.sandboxspringboot.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final String id) {
        super("User with the id : " + id + " not found.");
    }
}
