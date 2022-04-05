package com.recipes.backend.exception.domain;

public class MissingQuantityException extends RuntimeException{

    private static final long serialVersionUID = -1871533420241310383L;

    public MissingQuantityException() {
        super("The quantity is missing in the provided ingredient");
    }
}
