package com.recipes.backend.exception.domain;

public class IngredientDuplicateException extends RuntimeException {

    private static final long serialVersionUID = -2304355796951517095L;

    public IngredientDuplicateException(final String name)
    {
        super(String.format("Ingredient with name: %s does exist in database", name));
    }
}
