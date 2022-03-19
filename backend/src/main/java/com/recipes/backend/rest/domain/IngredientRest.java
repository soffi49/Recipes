package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientRest implements Serializable {

    private static final long serialVersionUID = -6252784107184220279L;

    @JsonProperty("id")
    private long id;

    @EqualsAndHashCode.Include
    @JsonProperty("name")
    private String name;

    @JsonProperty("photo")
    private String photo;

}
