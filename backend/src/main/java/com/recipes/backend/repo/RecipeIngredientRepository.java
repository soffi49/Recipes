package com.recipes.backend.repo;

import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends PagingAndSortingRepository<RecipeIngredientDTO, Long>
{
    @Modifying
    @Query(value = "INSERT INTO recipe_ingredient " +
            "(recipe_id, ingredient_id, quantity) VALUES " +
            "(:recipeId, :ingredientId, :quantity)", nativeQuery = true)
    void addIngredientForRecipe(@Param("recipeId") long recipeId,
                                @Param("ingredientId") long ingredientId,
                                @Param("quantity") String quantity);

    @Modifying
    @Query(value = "DELETE FROM recipe_ingredient WHERE recipe_id = :recipeId", nativeQuery = true)
    void deleteAllByRecipeId(@Param("recipeId") Long recipeId);
}
