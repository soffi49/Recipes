package com.recipes.backend.bizz.ingredient;


import com.recipes.backend.bizz.ingredient.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    void addIngredient(Ingredient ingredient);

    Set<Ingredient> getAllIngredients(Integer page, Integer limit);

    Ingredient updateIngredient(Ingredient ingredient);
}
