package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.rest.domain.IngredientRest;

import java.util.Objects;
import java.util.Optional;

public class IngredientMapper {

    public static Optional<Ingredient> mapToIngredient(final IngredientRest ingredientRest) {

        if(Objects.nonNull(ingredientRest)) {
            final Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(ingredientRest.getId());
            ingredient.setName(ingredientRest.getName());

            if(Objects.nonNull(ingredientRest.getPhoto())) {
                ingredient.setPhoto(ingredientRest.getPhoto());
            }
            return Optional.of(ingredient);
        }
        return Optional.empty();
    }

    public static Optional<Ingredient> mapToIngredient (final IngredientDTO ingredientDTO) {

        if(Objects.nonNull(ingredientDTO)) {
            final Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(ingredientDTO.getIngredientId());
            ingredient.setName(ingredientDTO.getName());

            if(Objects.nonNull(ingredientDTO.getPhoto())) {
                ingredient.setPhoto(ingredientDTO.getPhoto());
            }
            return Optional.of(ingredient);
        }
        return Optional.empty();
    }

    public static Optional<IngredientDTO> mapToIngredientDTO (final Ingredient ingredient) {

        if(Objects.nonNull(ingredient)) {
            final IngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setName(ingredient.getName());

            if(Objects.nonNull(ingredient.getPhoto())) {
                ingredientDTO.setPhoto(ingredient.getPhoto());
            }
            return Optional.of(ingredientDTO);
        }
        return Optional.empty();
    }

    public static Optional<IngredientRest> mapToIngredientRest (final IngredientDTO ingredientDTO) {

        if(Objects.nonNull(ingredientDTO)) {
            final IngredientRest ingredientRest = new IngredientRest();
            ingredientRest.setId(ingredientDTO.getIngredientId());
            ingredientRest.setName(ingredientDTO.getName());

            if(Objects.nonNull(ingredientDTO.getPhoto())) {
                ingredientRest.setPhoto(ingredientDTO.getPhoto());
            }
            return Optional.of(ingredientRest);
        }
        return Optional.empty();
    }
}
