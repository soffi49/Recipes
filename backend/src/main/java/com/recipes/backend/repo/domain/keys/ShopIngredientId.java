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
public class ShopIngredientId implements Serializable {

    @Column(name = "shop_id")
    private long shopId;

    @Column(name = "ingredient_id")
    private long ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopIngredientId that = (ShopIngredientId) o;
        return shopId == that.shopId && ingredientId == that.ingredientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId, ingredientId);
    }
}
