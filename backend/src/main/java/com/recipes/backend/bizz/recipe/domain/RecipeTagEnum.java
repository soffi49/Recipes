package com.recipes.backend.bizz.recipe.domain;

import java.util.Arrays;

public enum RecipeTagEnum {
    VEGETARIAN(1, "vegetarian"),
    GLUTEN_FREE(2, "gluten free"),
    LOW_CALORIE(3, "low calorie"),
    NO_LACTOSE(4, "no lactose");

    private final long id;

    private final String name;

    RecipeTagEnum(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RecipeTagEnum findTagById(final long id) {
        return Arrays.stream(RecipeTagEnum.values())
                .filter(tag -> tag.id == id)
                .findFirst()
                .orElse(null);
    }
}
