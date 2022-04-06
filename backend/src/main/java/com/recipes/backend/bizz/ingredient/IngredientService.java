package com.recipes.backend.bizz.ingredient;


import com.recipes.backend.bizz.ingredient.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    void addIngredient(Ingredient ingredient);

    boolean deleteIngredient(Long ingredientId);

    Set<Ingredient> getAllIngredients(final Integer page,
                                      final Integer limit,
                                      final Long ingredientId,
                                      final String name);

    long getIngredientsCount();

    boolean isIngredientPresent(long ingredientId);

    Ingredient updateIngredient(Ingredient ingredient);
}
