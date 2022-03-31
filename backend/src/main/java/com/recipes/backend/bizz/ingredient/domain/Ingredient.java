package com.recipes.backend.bizz.ingredient.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
@Setter
public class Ingredient {

    private long ingredientId;

    private String name;

    private String quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
