package com.recipes.backend.repo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ingredient")
public class IngredientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingredient_id")
    private long ingredientId;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "ingredientSet")
    private Set<RecipeDTO> recipeSet;

    @OneToMany(mappedBy = "ingredient")
    private Set<ShopIngredientDTO> shopSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientDTO that = (IngredientDTO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
