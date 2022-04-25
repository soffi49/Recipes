package com.recipes.backend.exception.domain;

public class RecipeDuplicateException extends RuntimeException
{
    private static final long serialVersionUID = 1191064759273136273L;

    public RecipeDuplicateException(final String name)
    {
        super(String.format("Recipe with name: %s does exist in database", name));
    }
}
