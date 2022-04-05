package com.recipes.backend.exception.domain;

public class IngredientNotFound extends RuntimeException
{
    private static final long serialVersionUID = 7034907109306356819L;

    public IngredientNotFound(final long ingredientId)
    {
        super(String.format("Ingredient with the id: %d does not exist", ingredientId));
    }
}
