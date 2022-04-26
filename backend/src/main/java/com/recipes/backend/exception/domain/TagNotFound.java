package com.recipes.backend.exception.domain;

public class TagNotFound extends RuntimeException
{
    private static final long serialVersionUID = 4654979723287663915L;

    public TagNotFound(final long tagId)
    {
        super(String.format("Tag with the id: %d does not exist", tagId));
    }
}
