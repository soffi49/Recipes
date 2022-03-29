package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRest {

    @NotNull
    @JsonProperty(value = "username", required = true)
    private String username;

    @NotNull
    @JsonProperty(value = "password", required = true)
    private String password;

}
