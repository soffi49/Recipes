package com.recipes.backend.repo.domain;

import com.recipes.backend.repo.domain.keys.RestaurantRecipeId;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "restaurant_recipe")
public class RestaurantRecipeDTO {

    @EmbeddedId
    private RestaurantRecipeId restaurantRecipeId;

    @NotNull
    @Column(name = "price")
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id")
    private RestaurantDTO restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private RecipeDTO recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantRecipeDTO that = (RestaurantRecipeDTO) o;
        return restaurantRecipeId.equals(that.restaurantRecipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantRecipeId);
    }
}
