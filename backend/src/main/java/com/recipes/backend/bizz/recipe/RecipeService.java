package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.sun.istack.Nullable;

import java.util.List;
import java.util.Set;

public interface RecipeService {

    boolean deleteRecipe(Long recipeId);

    List<Recipe> getAllRecipes(Integer page,
                               Integer limit,
                               @Nullable String name,
                               @Nullable Set<String> tags);

    void addRecipe(Recipe recipe);

    long getRecipesCount();

    void updateRecipe(Recipe recipe);

    List<Recipe> findRecipes(List<Ingredient> ingredients);
}
