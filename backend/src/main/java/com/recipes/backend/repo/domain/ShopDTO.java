package com.recipes.backend.repo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shop")
public class ShopDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    private long shopId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "shop")
    private Set<ShopIngredientDTO> ingredientSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopDTO shopDTO = (ShopDTO) o;
        return name.equals(shopDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
