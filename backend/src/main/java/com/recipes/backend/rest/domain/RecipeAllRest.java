package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeAllRest implements Serializable {

    private static final long serialVersionUID = -6175510528256805403L;

    @NotNull
    @JsonProperty("total_recipes")
    private long totalRecipes;

    @NotNull
    @JsonProperty("recipes")
    private Set<RecipeRest> recipes;
}
