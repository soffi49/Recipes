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
public class IngredientAllRest implements Serializable {

    @NotNull
    @JsonProperty("total_ingredients")
    private long totalIngredients;

    @NotNull
    @JsonProperty("ingredients")
    private Set<IngredientRest> ingredients;

}
