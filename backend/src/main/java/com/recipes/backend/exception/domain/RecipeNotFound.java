package com.recipes.backend.exception.domain;

public class RecipeNotFound extends RuntimeException
{
    private static final long serialVersionUID = -5732701931372820539L;

    public RecipeNotFound(final long recipeId)
    {
        super(String.format("Recipe with the id: %d does not exist", recipeId));
    }
}
