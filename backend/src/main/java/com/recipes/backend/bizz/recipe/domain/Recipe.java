package com.recipes.backend.bizz.recipe.domain;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
public class Recipe {

    private long recipeId;

    private Set<RecipeTypeEnum> tags;

    private List<Ingredient> ingredients;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
