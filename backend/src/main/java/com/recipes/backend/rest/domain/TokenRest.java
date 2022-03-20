package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenRest {

    @JsonProperty("token")
    private final String token;

    public TokenRest(String token) {
        this.token = token;
    }
}
