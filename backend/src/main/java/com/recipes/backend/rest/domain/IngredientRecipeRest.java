package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientRecipeRest implements Serializable {

    private static final long serialVersionUID = -8011641984436185040L;

    @NotNull
    @JsonProperty(value = "ingredient", required = true)
    private IngredientRest ingredient;

    @NotNull
    @JsonProperty(value = "quantity", required = true)
    private String quantity;
}
