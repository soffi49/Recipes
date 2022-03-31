package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.repo.domain.RecipeDTO;
import com.recipes.backend.rest.domain.RecipeRest;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static Optional<Recipe> mapToRecipe(final RecipeDTO recipeDTO) {

        if (Objects.nonNull(recipeDTO)) {
            final Recipe recipe = new Recipe();
            recipe.setRecipeId(recipeDTO.getRecipeId());
            recipe.setName(recipeDTO.getName());
            recipe.setInstructions(recipeDTO.getInstructions());
            recipe.setTags(recipeDTO.getTagSet().stream()
                    .map(tag -> RecipeTagEnum.findTagById(tag.getTagId()))
                    .collect(Collectors.toSet()));
            recipe.setIngredients(recipeDTO.getIngredientSet().stream()
                    .map(ingredient ->
                            IngredientMapper.mapToIngredient(ingredient.getIngredient(), ingredient.getQuantity()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet()));
            return Optional.of(recipe);
        }
        return Optional.empty();
    }

    public static Optional<RecipeRest> mapToRecipeRest(final Recipe recipe) {

        if (Objects.nonNull(recipe)) {
            final RecipeRest recipeRest = new RecipeRest();
            final Set<RecipeTagEnum> tagSet = Objects.isNull(recipe.getTags()) ? Collections.emptySet() : recipe.getTags();
            final Set<Ingredient> ingredientSet = Objects.isNull(recipe.getIngredients()) ? Collections.emptySet() : recipe.getIngredients();

            recipeRest.setId(recipe.getRecipeId());
            recipeRest.setName(recipe.getName());
            recipeRest.setTags(tagSet.stream().map(RecipeTagEnum::getName).collect(Collectors.toSet()));
            recipeRest.setInstructions(recipe.getInstructions());
            recipeRest.setIngredients(ingredientSet.stream()
                    .map(IngredientMapper::mapToIngredientRecipeRest)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet()));
            return Optional.of(recipeRest);
        }
        return Optional.empty();
    }
}
