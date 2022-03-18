package com.recipes.backend.repo.domain;

import com.recipes.backend.repo.domain.keys.ShopIngredientId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "shop_ingredient")
public class ShopIngredientDTO {

    @EmbeddedId
    private ShopIngredientId shopIngredientId;

    @Column(name = "price")
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("shopId")
    @JoinColumn(name = "shop_id")
    private ShopDTO shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private IngredientDTO ingredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopIngredientDTO that = (ShopIngredientDTO) o;
        return shopIngredientId.equals(that.shopIngredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopIngredientId);
    }
}
