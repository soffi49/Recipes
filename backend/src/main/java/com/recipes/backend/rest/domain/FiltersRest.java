package com.recipes.backend.rest.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltersRest implements Serializable
{
    private static final long serialVersionUID = 794632159867963868L;

    @JsonProperty(value = "name")
    @EqualsAndHashCode.Include
    private String name;

    @JsonProperty(value = "tags")
    @EqualsAndHashCode.Include
    private Set<String> tags;

}
