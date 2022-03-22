package com.recipes.backend.exception.domain;

public class DatabaseFindException extends RuntimeException{

    private static final long serialVersionUID = 512839807614871001L;

    public DatabaseFindException(final String message) {
        super(String.format("Internal database error while reading data: %s", message));
    }
}
