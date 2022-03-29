package com.recipes.backend.exception.domain;

public class IngredientEmptyException extends RuntimeException{

    private static final long serialVersionUID = -1758358840739830705L;

    public IngredientEmptyException() {
        super("The given ingredient is empty");
    }
}
