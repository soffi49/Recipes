package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeRest implements Serializable {

    private static final long serialVersionUID = -6623215843380843476L;

    @NotNull
    @JsonProperty("id")
    private Long id;

    @NotNull
    @EqualsAndHashCode.Include
    @JsonProperty(value = "name", required = true)
    private String name;

    @NotNull
    @JsonProperty(value = "instructions", required = true)
    private String instructions;

    @NotNull
    @JsonProperty(value = "ingredients", required = true)
    private Set<IngredientRecipeRest> ingredients;

    @NotNull
    @JsonProperty(value = "tags")
    private Set<String> tags;
}

