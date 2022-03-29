package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

public class TokenRest {

    @NotNull
    @JsonProperty(value = "token", required = true)
    private final String token;

    public TokenRest(String token) {
        this.token = token;
    }
}
