package com.recipes.backend.repo.domain.keys;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RestaurantRecipeId implements Serializable {

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "recipe_id")
    private long recipeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantRecipeId that = (RestaurantRecipeId) o;
        return restaurantId == that.restaurantId && recipeId == that.recipeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, recipeId);
    }
}
