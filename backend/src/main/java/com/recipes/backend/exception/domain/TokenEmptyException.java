package com.recipes.backend.exception.domain;

public class TokenEmptyException extends RuntimeException {

    private static final long serialVersionUID = 8179000877041638454L;

    public TokenEmptyException() {
        super("The given token have some fields which are empty");
    }
}
