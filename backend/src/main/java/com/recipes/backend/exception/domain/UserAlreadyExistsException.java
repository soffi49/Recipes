package com.recipes.backend.exception.domain;

public class UserAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(final String userName) {
        super(String.format("User %s already exists", userName));
    }

}
