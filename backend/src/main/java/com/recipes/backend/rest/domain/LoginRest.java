package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRest {

    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

}
