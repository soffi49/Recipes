package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.exception.domain.IngredientNotFound;
import com.recipes.backend.exception.domain.RecipeNotFound;
import com.recipes.backend.repo.RecipeRepository;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import com.recipes.backend.repo.domain.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@Sql({"/data/drop-db-if-exists.sql", "/data/create-db.sql", "/data/recipe/insert-1-recipe.sql"})
class RecipeServiceIntegrationTest extends AbstractIntegrationTestConfig
{

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    private Recipe mockRecipe;

    @BeforeEach
    public void setUp()
    {
        final Ingredient mockIngredient = new Ingredient();
        mockIngredient.setQuantity("10g");
        mockIngredient.setName("Ingredient");
        mockIngredient.setIngredientId(1000L);

        mockRecipe = new Recipe();
        mockRecipe.setRecipeId(1000L);
        mockRecipe.setName("Recipe");
        mockRecipe.setInstructions("Test instruction");
        mockRecipe.setTags(Set.of(RecipeTagEnum.VEGETARIAN));
        mockRecipe.setIngredients(Set.of(mockIngredient));
    }

    @Test
    @DisplayName("Get all recipes one page withour filters")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    void getAllRecipesOnePageWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit, null, null);
        final Set<String> recipesNames = retrievedList.stream().map(Recipe::getName).collect(Collectors.toSet());

        assertThat(retrievedList.size()).isEqualTo(5);
        assertThat(recipesNames).contains("Recipe2");
    }

    @Test
    @DisplayName("Get all recipes more pages without filters")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    void getAllRecipesMorePagesWithoutFilters()
    {
        final int limit = 3;

        final Set<Recipe> retrievedList1 = recipeService.getAllRecipes(0, limit, null, null);
        final Set<Recipe> retrievedList2 = recipeService.getAllRecipes(1, limit, null, null);

        assertThat(retrievedList1.size()).isEqualTo(3);
        assertThat(retrievedList2.size()).isEqualTo(2);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Recipe5"))).isFalse();
    }

    @Test
    @DisplayName("Get all recipes empty without filters")
    @Sql({"/data/recipe/truncate-recipes.sql"})
    void getAllRecipesEmptyWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit, null, null);

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Count recipes non empty")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    void countIngredientsNonEmpty()
    {
        final long retrievedList = recipeService.getRecipesCount();

        assertThat(retrievedList).isEqualTo(5);
    }

    @Test
    @DisplayName("Count ingredients empty")
    @Sql({"/data/create-db.sql", "/data/recipe/truncate-recipes.sql"})
    void countIngredientsEmpty()
    {
        final long retrievedList = recipeService.getRecipesCount();

        assertThat(retrievedList).isZero();
    }

    @Test
    @DisplayName("Update recipe modify name")
    @Sql({"/data/create-db.sql", "/data/recipe/truncate-recipes.sql", "/data/recipe/insert-1-recipe.sql"})
    void updateRecipeModifyName()
    {
        final String recipeNameBefore = recipeRepository.findById(1000L).get().getName();
        final String newName = "Name after";

        mockRecipe.setName(newName);
        recipeService.updateRecipe(mockRecipe);

        final String recipeNameAfter = recipeRepository.findById(1000L).get().getName();

        assertThat(recipeNameBefore).isEqualTo("Recipe");
        assertThat(recipeNameAfter).isEqualTo(newName);
    }

    @Test
    @DisplayName("Update recipe modify tags")
    @Sql({"/data/create-db.sql", "/data/recipe/truncate-recipes.sql", "/data/recipe/insert-1-recipe.sql", "/data/recipe/insert-1-recipe-tag.sql"})
    void updateRecipeModifyTags()
    {
        final int recipeTagsBefore = recipeRepository.findById(1000L).get().getTagSet().size();

        mockRecipe.setTags(Set.of(RecipeTagEnum.VEGETARIAN, RecipeTagEnum.GLUTEN_FREE));
        recipeService.updateRecipe(mockRecipe);

        final int recipeTagsAfter = recipeRepository.findById(1000L).get().getTagSet().size();

        assertThat(recipeTagsBefore).isEqualTo(1);
        assertThat(recipeTagsAfter).isEqualTo(2);
        assertThat(recipeRepository.findById(1000L).get().getTagSet().stream()).anyMatch(tag -> tag.getName().equals("gluten free"));
    }

    @Test
    @DisplayName("Update recipe - recipe not found")
    @Sql({"/data/create-db.sql", "/data/recipe/truncate-recipes.sql", "/data/recipe/insert-1-recipe.sql"})
    void updateRecipeNotFound()
    {
        mockRecipe.setRecipeId(1000000L);

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(RecipeNotFound.class)
                .hasMessage("Recipe with the id: 1000000 does not exist");
    }

    @Test
    @DisplayName("Update recipe - ingredient not found")
    @Sql({"/data/create-db.sql", "/data/recipe/truncate-recipes.sql", "/data/recipe/insert-1-recipe.sql"})
    void updateRecipeIngredientNotFound()
    {
        final Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(2000000L);
        ingredient.setName("Wrong ingredient");
        ingredient.setQuantity("10g");

        final Set<Ingredient> newIngredients = new HashSet<>();
        newIngredients.addAll(Set.of(ingredient));
        newIngredients.addAll(mockRecipe.getIngredients());
        mockRecipe.setIngredients(newIngredients);

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(IngredientNotFound.class)
                .hasMessage("Ingredient with the id: 2000000 does not exist");
    }
}
