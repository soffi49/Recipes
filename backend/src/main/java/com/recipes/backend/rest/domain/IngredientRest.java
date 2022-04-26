package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientRest implements Serializable {

    private static final long serialVersionUID = 780145697608886440L;

    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @EqualsAndHashCode.Include
    @JsonProperty(value = "name", required = true)
    private String name;

}
