package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TokenRest {

    @NotNull
    @JsonProperty(value = "token", required = true)
    private final String token;

    public TokenRest(String token) {
        this.token = token;
    }
}
