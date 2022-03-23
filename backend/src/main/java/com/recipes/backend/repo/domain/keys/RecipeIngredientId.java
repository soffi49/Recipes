package com.recipes.backend.repo.domain.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RecipeIngredientId implements Serializable {

    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "ingredient_id")
    private long ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientId that = (RecipeIngredientId) o;
        return recipeId == that.recipeId && ingredientId == that.ingredientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }
}
