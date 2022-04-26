package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class TokenRest {

    @NotNull
    @JsonProperty(value = "token", required = true)
    private final String token;

    @NotNull
    @JsonProperty(value = "isAdmin", required = true)
    private final Integer isAdmin;

    public TokenRest(String token, Integer isAdmin) {
        this.token = token;
        this.isAdmin = isAdmin;
    }

}
