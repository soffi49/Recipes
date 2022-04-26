package com.recipes.backend.exception.domain;

public class RecipeEmptyException extends RuntimeException
{
    private static final long serialVersionUID = 8334975981220177579L;

    public RecipeEmptyException()
    {
        super("The given recipe have some fields which are empty");
    }
}
