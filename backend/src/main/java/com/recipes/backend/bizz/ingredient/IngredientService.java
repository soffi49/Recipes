package com.recipes.backend.bizz.ingredient;


import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

public interface IngredientService
{

    void addIngredient(Ingredient ingredient);

    boolean deleteIngredient(Long ingredientId);

    List<Ingredient> getAllIngredients(Integer page,
                                       Integer limit,
                                       @Nullable String name);

    long getIngredientsCount();

    boolean isIngredientPresent(long ingredientId);

    void updateIngredient(Ingredient ingredient);
}
