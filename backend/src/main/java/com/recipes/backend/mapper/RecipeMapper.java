package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.exception.domain.MissingQuantityException;
import com.recipes.backend.repo.domain.RecipeDTO;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import com.recipes.backend.rest.domain.IngredientRecipeRest;
import com.recipes.backend.rest.domain.RecipeRest;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.recipes.backend.mapper.IngredientMapper.mapToIngredientDTO;

public class RecipeMapper
{

    public static Optional<Recipe> mapToRecipe(final RecipeDTO recipeDTO)
    {

        if (Objects.nonNull(recipeDTO))
        {
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

    public static Optional<Recipe> mapToRecipe(final RecipeRest recipeRest)
    {

        if (Objects.nonNull(recipeRest))
        {
            final Recipe recipe = new Recipe();
            final Set<String> tagSet = Objects.isNull(recipeRest.getTags()) ? Collections.emptySet() : recipeRest.getTags();
            final Set<IngredientRecipeRest> ingredientSet = Objects.isNull(recipeRest.getIngredients()) ? Collections.emptySet() : recipeRest.getIngredients();

            if (Objects.nonNull(recipeRest.getId()))
            {
                recipe.setRecipeId(recipeRest.getId());
            }
            recipe.setName(recipeRest.getName());
            recipe.setInstructions(recipeRest.getInstructions());
            recipe.setTags(tagSet.stream().map(RecipeTagEnum::findTagByName).collect(Collectors.toSet()));
            recipe.setIngredients(ingredientSet.stream()
                                          .map(IngredientMapper::mapToIngredient)
                                          .filter(Optional::isPresent)
                                          .map(Optional::get)
                                          .collect(Collectors.toSet()));
            return Optional.of(recipe);
        }
        return Optional.empty();
    }

    public static Optional<RecipeRest> mapToRecipeRest(final Recipe recipe)
    {

        if (Objects.nonNull(recipe))
        {
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

    public static Optional<RecipeDTO> mapToRecipeDTO(final Recipe recipe)
    {

        if (Objects.nonNull(recipe))
        {
            final RecipeDTO recipeDTO = new RecipeDTO();
            recipeDTO.setName(recipe.getName());
            recipeDTO.setInstructions(recipe.getInstructions());
            recipeDTO.setTagSet(
                    recipe.getTags().stream()
                            .map(TagMapper::mapToTagDTO)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toSet()));
            recipeDTO.setIngredientSet(
                    recipe.getIngredients().stream()
                            .map(ingredient -> {
                                final RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
                                recipeIngredientDTO.setRecipe(recipeDTO);
                                recipeIngredientDTO.setIngredient(mapToIngredientDTO(ingredient).orElseThrow(IngredientEmptyException::new));
                                if(Objects.isNull(ingredient.getQuantity()))
                                {
                                    throw new MissingQuantityException();
                                }
                                recipeIngredientDTO.setQuantity(ingredient.getQuantity());
                                return recipeIngredientDTO;
                            })
                            .collect(Collectors.toSet()));
            return Optional.of(recipeDTO);
        }
        return Optional.empty();
    }
}
