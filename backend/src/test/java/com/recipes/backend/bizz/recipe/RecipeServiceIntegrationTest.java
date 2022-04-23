package com.recipes.backend.bizz.recipe;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.exception.domain.IngredientNotFound;
import com.recipes.backend.exception.domain.RecipeDuplicateException;
import com.recipes.backend.exception.domain.RecipeNotFound;
import com.recipes.backend.repo.RecipeRepository;
import com.recipes.backend.repo.domain.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

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

    @Transactional
    @Test
    @DisplayName("Get all recipes one page without filters")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-5-recipes.sql"})
    @Order(1)
    void getAllRecipesOnePageWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit, null, null);
        final Set<String> recipesNames = retrievedList.stream().map(Recipe::getName).collect(Collectors.toSet());

        assertThat(retrievedList).hasSize(5);
        assertThat(recipesNames).contains("Recipe2");
    }

    @Transactional
    @Test
    @DisplayName("Get all recipes more pages without filters")
    @Sql("/data/recipe/insert-5-recipes.sql")
    @Order(2)
    void getAllRecipesMorePagesWithoutFilters()
    {
        final int limit = 3;

        final Set<Recipe> retrievedList1 = recipeService.getAllRecipes(0, limit, null, null);
        final Set<Recipe> retrievedList2 = recipeService.getAllRecipes(1, limit, null, null);

        assertThat(retrievedList1).hasSize(3);
        assertThat(retrievedList2).hasSize(2);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Recipe5"))).isFalse();
    }

    @Test
    @DisplayName("Get all recipes empty without filters")
    @Sql({"/data/truncate-db.sql"})
    @Order(3)
    void getAllRecipesEmptyWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Recipe> retrievedList = recipeService.getAllRecipes(page, limit, null, null);

        assertThat(retrievedList).isEmpty();
    }

    @Test
    @DisplayName("Count recipes non empty")
    @Sql({"/data/recipe/insert-5-recipes.sql"})
    @Order(4)
    void countIngredientsNonEmpty()
    {
        final long retrievedList = recipeService.getRecipesCount();

        assertThat(retrievedList).isEqualTo(5);
    }

    @Test
    @DisplayName("Count ingredients empty")
    @Sql({"/data/truncate-db.sql"})
    @Order(5)
    void countIngredientsEmpty()
    {
        final long retrievedList = recipeService.getRecipesCount();

        assertThat(retrievedList).isZero();
    }

    @Test
    @DisplayName("Add recipe with correct data")
    @Sql({"/data/recipe/insert-1-recipe-ingredient.sql"})
    @Order(6)
    void addRecipeCorrectData()
    {
        assertThatNoException().isThrownBy(() -> recipeService.addRecipe(mockRecipe));

        final List<RecipeDTO> databaseRecipes = (List<RecipeDTO>) recipeRepository.findAll();
        assertThat(databaseRecipes).anyMatch(el -> el.getName().equals("Recipe"));
    }

    @Test
    @DisplayName("Add recipe duplicate")
    @Order(7)
    void addIngredientDuplicate()
    {
        assertThatThrownBy(() -> recipeService.addRecipe(mockRecipe))
                .isExactlyInstanceOf(RecipeDuplicateException.class)
                .hasMessage("Recipe with name: Recipe does exist in database");
    }

    @Test
    @DisplayName("Delete one not existing recipe")
    @Sql("/data/truncate-db.sql")
    @Order(8)
    void deleteOneNotExistingRecipe()
    {
        assertThatThrownBy(() -> recipeService.deleteRecipe(1L))
                .isExactlyInstanceOf(RecipeNotFound.class)
                .hasMessage("Recipe with the id: 1 does not exist");
    }

    @Test
    @DisplayName("Delete one existing recipe")
    @Sql("/data/recipe/insert-1-recipe.sql")
    @Order(9)
    void deleteOneProperRecipe()
    {
        assertThat(recipeService.deleteRecipe((long) 1000)).isTrue();

        final List<RecipeDTO> ingredientList = (List<RecipeDTO>) recipeRepository.findAll();
        assertThat(ingredientList).isEmpty();

    }


    @Test
    @DisplayName("Update recipe modify name")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-1-recipe.sql"})
    @Order(10)
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

    @Transactional
    @Test
    @DisplayName("Update recipe modify tags")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-1-recipe.sql"})
    @Order(11)
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
    @Order(12)
    void updateRecipeNotFound()
    {
        mockRecipe.setRecipeId(1000000L);

        assertThatThrownBy(() -> recipeService.updateRecipe(mockRecipe))
                .isExactlyInstanceOf(RecipeNotFound.class)
                .hasMessage("Recipe with the id: 1000000 does not exist");
    }

    @Test
    @DisplayName("Update recipe - ingredient not found")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-1-recipe.sql"})
    @Order(13)
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
