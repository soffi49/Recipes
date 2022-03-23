package com.recipes.backend.repo.domain;

import com.recipes.backend.repo.domain.keys.RecipeIngredientId;
import com.recipes.backend.repo.domain.keys.RestaurantRecipeId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredientDTO {

    @EmbeddedId
    private RecipeIngredientId recipeIngredientId;

    @NotNull
    @Column(name = "quantity")
    private String quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private RecipeDTO recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private IngredientDTO ingredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientDTO that = (RecipeIngredientDTO) o;
        return Objects.equals(recipeIngredientId, that.recipeIngredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeIngredientId);
    }
}
