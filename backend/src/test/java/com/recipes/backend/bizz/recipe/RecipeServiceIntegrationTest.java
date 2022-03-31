package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Sql({"/data/drop-db-if-exists.sql", "/data/create-db.sql", "/data/recipe/insert-1-recipe.sql"})
public class RecipeServiceIntegrationTest extends AbstractIntegrationTestConfig {

    @Autowired
    private RecipeService recipeService;

    @Test
    @DisplayName("Get all recipes one page")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    void getAllRecipesOnePage() {
        final int limit = 5;
        final int page = 0;

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit);
        final Set<String> recipesNames = retrievedList.stream().map(Recipe::getName).collect(Collectors.toSet());

        assertThat(retrievedList.size()).isEqualTo(5);
        assertThat(recipesNames).contains("Recipe2");
    }

    @Test
    @DisplayName("Get all recipes more pages")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    void getAllRecipesMorePages() {
        final int limit = 3;

        final Set<Recipe> retrievedList1 = recipeService.getAllRecipes(0, limit);
        final Set<Recipe> retrievedList2 = recipeService.getAllRecipes(1, limit);

        assertThat(retrievedList1.size()).isEqualTo(3);
        assertThat(retrievedList2.size()).isEqualTo(2);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Recipe5"))).isFalse();
    }

    @Test
    @DisplayName("Get all recipes empty")
    @Sql({"/data/recipe/truncate-recipes.sql"})
    void getAllRecipesEmpty() {
        final int limit = 5;
        final int page = 0;

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit);

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Count recipes non empty")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    void countIngredientsNonEmpty() {
        final long retrievedList = recipeService.getRecipesCount();

        assertThat(retrievedList).isEqualTo(5);
    }

    @Test
    @DisplayName("Count ingredients empty")
    @Sql({"/data/create-db.sql", "/data/recipe/truncate-recipes.sql"})
    void countIngredientsEmpty() {
        final long retrievedList = recipeService.getRecipesCount();

        assertThat(retrievedList).isZero();
    }
}
