package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    boolean deleteRecipe(Long recipeId);

    Set<Recipe> getAllRecipes(Integer page, Integer limit);

    long getRecipesCount();
}
