package com.recipes.backend.exception.domain;

public class RecipeEmptyException extends RuntimeException{

    private static final long serialVersionUID = -1758358840739830705L;

    public RecipeEmptyException() {
        super("The given recipe have some fields which are empty");
    }
}