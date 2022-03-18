package com.recipes.backend.repo.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "recipe")
public class RecipeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private long recipeId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "instructions")
    private String instructions;

    @ManyToMany
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn( name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<IngredientDTO> ingredientSet;

    @ManyToMany
    @JoinTable(
            name = "recipe_tag",
            joinColumns = @JoinColumn( name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagDTO> tagSet;

    @ManyToMany(mappedBy = "recipeSet")
    private Set<UserDTO> userSet;

    @OneToMany(mappedBy = "recipe")
    private Set<RestaurantRecipeDTO> restaurantSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeDTO recipeDTO = (RecipeDTO) o;
        return name.equals(recipeDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
