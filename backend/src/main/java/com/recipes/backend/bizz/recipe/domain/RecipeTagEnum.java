package com.recipes.backend.bizz.recipe.domain;

import java.util.Arrays;

public enum RecipeTagEnum {
    VEGETARIAN(1),
    GLUTEN_FREE(2),
    LOW_CALORIE(3),
    NO_LACTOSE(4);

    private final long id;

    RecipeTagEnum(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public RecipeTagEnum findTagById (final long id) {
        return Arrays.stream(RecipeTagEnum.values())
                .filter(tag -> tag.id == id)
                .findFirst()
                .orElse(null);
    }
}
