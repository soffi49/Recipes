package com.recipes.backend.exception.domain;

public class UserEmptyException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public UserEmptyException() {
        super("The given user have some fields which are empty");
    }
}
