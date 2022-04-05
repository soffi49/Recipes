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

    private static final long serialVersionUID = 7810709014293012771L;

    @NotNull
    @JsonProperty(value = "total_ingredients", required = true)
    private long totalIngredients;

    @NotNull
    @JsonProperty(value = "ingredients", required = true)
    private Set<IngredientRest> ingredients;

}
