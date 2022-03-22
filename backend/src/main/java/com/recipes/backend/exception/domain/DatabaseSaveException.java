package com.recipes.backend.exception.domain;

public class DatabaseSaveException extends RuntimeException{

    private static final long serialVersionUID = 4385306190738886535L;

    public DatabaseSaveException(final String message) {
        super(String.format("Internal database error while saving: %s", message));
    }
}
