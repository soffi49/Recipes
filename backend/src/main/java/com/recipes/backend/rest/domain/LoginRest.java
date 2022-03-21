package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRest {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

}
