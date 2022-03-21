package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.rest.domain.IngredientRest;

import java.util.Objects;
import java.util.Optional;

public class IngredientMapper {

    public static Optional<Ingredient> mapToIngredient(final IngredientRest ingredientRest) {

        if (Objects.nonNull(ingredientRest)) {
            final Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(ingredientRest.getId());
            ingredient.setName(ingredientRest.getName());
            return Optional.of(ingredient);
        }
        return Optional.empty();
    }

    public static Optional<Ingredient> mapToIngredient(final IngredientDTO ingredientDTO) {

        if (Objects.nonNull(ingredientDTO)) {
            final Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(ingredientDTO.getIngredientId());
            ingredient.setName(ingredientDTO.getName());
            return Optional.of(ingredient);
        }
        return Optional.empty();
    }

    public static Optional<IngredientDTO> mapToIngredientDTO(final Ingredient ingredient) {

        if (Objects.nonNull(ingredient)) {
            final IngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setName(ingredient.getName());
            return Optional.of(ingredientDTO);
        }
        return Optional.empty();
    }

    public static Optional<IngredientRest> mapToIngredientRest(final Ingredient ingredient) {

        if (Objects.nonNull(ingredient)) {
            final IngredientRest ingredientRest = new IngredientRest();
            ingredientRest.setId(ingredient.getIngredientId());
            ingredientRest.setName(ingredient.getName());
            return Optional.of(ingredientRest);
        }
        return Optional.empty();
    }
}
