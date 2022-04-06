package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.exception.domain.MissingQuantityException;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.repo.domain.RecipeDTO;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import com.recipes.backend.repo.domain.TagDTO;
import com.recipes.backend.rest.domain.IngredientRecipeRest;
import com.recipes.backend.rest.domain.IngredientRest;
import com.recipes.backend.rest.domain.RecipeRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class RecipeMapperTest {

    private RecipeDTO mockRecipeDTO;
    private Recipe mockRecipe;
    private RecipeRest mockRecipeRest;

    @BeforeEach
    public void setUp() {
        setUpRecipe();
        setUpRecipeDTO();
        setUpRecipeRest();
    }

    @Test
    @DisplayName("Map to recipe from recipeDTO not null")
    void mapToRecipeFromRecipeDTO() {
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe(mockRecipeDTO);

        Assertions.assertTrue(retrievedRecipe.isPresent());
        Assertions.assertEquals(RecipeTagEnum.VEGETARIAN, retrievedRecipe.get().getTags().stream().findFirst().get());
        Assertions.assertEquals("Test RecipeDTO", retrievedRecipe.get().getName());
        Assertions.assertEquals("Test Ingredient", retrievedRecipe.get().getIngredients().stream().findFirst().get().getName());
    }

    @Test
    @DisplayName("Map to recipe from recipeDTO without Tags")
    void mapToRecipeFromRecipeWithoutTags() {
        mockRecipeDTO.setTagSet(Collections.emptySet());
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe(mockRecipeDTO);

        Assertions.assertTrue(retrievedRecipe.isPresent());
        Assertions.assertEquals(0, retrievedRecipe.get().getTags().size());
    }

    @Test
    @DisplayName("Map to recipe from recipeDTO without ingredients")
    void mapToRecipeFromRecipeWithoutIngredients() {
        mockRecipeDTO.setIngredientSet(Collections.emptySet());
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe(mockRecipeDTO);

        Assertions.assertTrue(retrievedRecipe.isPresent());
        Assertions.assertEquals(0, retrievedRecipe.get().getIngredients().size());
    }

    @Test
    @DisplayName("Map to recipe from recipeDTO null")
    void mapToRecipeFromRecipeDTONull() {
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe((RecipeDTO) null);

        Assertions.assertTrue(retrievedRecipe.isEmpty());
    }

    @Test
    @DisplayName("Map to recipe from recipeRest all data")
    void mapToRecipeFromRecipeRestAllData() {
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe(mockRecipeRest);

        Assertions.assertTrue(retrievedRecipe.isPresent());
        Assertions.assertEquals(1, retrievedRecipe.get().getIngredients().size());
        Assertions.assertEquals("50g", retrievedRecipe.get().getIngredients().stream().findFirst().get().getQuantity());
    }

    @Test
    @DisplayName("Map to recipe from recipeRest without tags")
    void mapToRecipeFromRecipeRestWithoutTags() {
        mockRecipeRest.setTags(Collections.emptySet());
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe(mockRecipeRest);

        Assertions.assertTrue(retrievedRecipe.isPresent());
        Assertions.assertEquals(0, retrievedRecipe.get().getTags().size());
        Assertions.assertEquals("Test RecipeRest", retrievedRecipe.get().getName());
    }

    @Test
    @DisplayName("Map to recipe from recipeRest without ingredients")
    void mapToRecipeFromRecipeRestWithoutIngredients() {
        mockRecipeRest.setIngredients(Collections.emptySet());
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe(mockRecipeRest);

        Assertions.assertTrue(retrievedRecipe.isPresent());
        Assertions.assertEquals(0, retrievedRecipe.get().getIngredients().size());
        Assertions.assertEquals("Test Instructions", retrievedRecipe.get().getInstructions());
    }

    @Test
    @DisplayName("Map to recipe from recipeRest null")
    void mapToRecipeFromRecipeRestNull() {
        final Optional<Recipe> retrievedRecipe = RecipeMapper.mapToRecipe((RecipeRest) null);

        Assertions.assertTrue(retrievedRecipe.isEmpty());
    }

    @Test
    @DisplayName("Map to recipeRest from recipe not null")
    void mapToRecipeRestFromRecipeNotNull() {
        mockRecipe.getIngredients().stream().findFirst().get().setQuantity("10g");
        final Optional<RecipeRest> retrievedRecipeRest = RecipeMapper.mapToRecipeRest(mockRecipe);

        Assertions.assertTrue(retrievedRecipeRest.isPresent());
        Assertions.assertEquals(2, retrievedRecipeRest.get().getTags().size());
        Assertions.assertEquals("Test Instructions", retrievedRecipeRest.get().getInstructions());
    }

    @Test
    @DisplayName("Map to recipeRest from recipe quantity null")
    void mapToRecipeRestFromRecipeQuantityNull() {
        assertThatThrownBy(() -> RecipeMapper.mapToRecipeRest(mockRecipe))
                .isExactlyInstanceOf(MissingQuantityException.class)
                .hasMessage("The quantity is missing in the provided ingredient");
    }

    @Test
    @DisplayName("Map to recipeRest from recipe is null")
    void mapToRecipeRestFromRecipeNull() {
        final Optional<RecipeRest> retrievedRecipeRest = RecipeMapper.mapToRecipeRest(null);

        Assertions.assertTrue(retrievedRecipeRest.isEmpty());
    }

    private void setUpRecipeDTO() {
        final IngredientDTO mockIngredientDTO = new IngredientDTO();
        mockIngredientDTO.setIngredientId(1);
        mockIngredientDTO.setName("Test Ingredient");

        final TagDTO tagDTO = new TagDTO();
        tagDTO.setName("vegetarian");
        tagDTO.setTagId(1);

        mockRecipeDTO = new RecipeDTO();
        mockRecipeDTO.setRecipeId(1);
        mockRecipeDTO.setInstructions("Test Instructions");
        mockRecipeDTO.setName("Test RecipeDTO");
        mockRecipeDTO.setTagSet(Set.of(tagDTO));

        final RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setIngredient(mockIngredientDTO);
        recipeIngredientDTO.setRecipe(mockRecipeDTO);
        recipeIngredientDTO.setQuantity("10g");

        mockRecipeDTO.setIngredientSet(Set.of(recipeIngredientDTO));
    }

    private void setUpRecipe() {
        final Ingredient mockIngredient = new Ingredient();
        mockIngredient.setIngredientId(1);
        mockIngredient.setName("Test Ingredient");

        mockRecipe = new Recipe();
        mockRecipe.setRecipeId(1);
        mockRecipe.setInstructions("Test Instructions");
        mockRecipe.setName("Test Recipe");
        mockRecipe.setTags(Set.of(RecipeTagEnum.VEGETARIAN, RecipeTagEnum.GLUTEN_FREE));
        mockRecipe.setIngredients(Set.of(mockIngredient));
    }

    private void setUpRecipeRest() {
        final IngredientRest ingredientRest = new IngredientRest();
        ingredientRest.setId(1L);
        ingredientRest.setName("Test Ingredient");

        final IngredientRecipeRest ingredientRecipeRest = new IngredientRecipeRest();
        ingredientRecipeRest.setQuantity("50g");
        ingredientRecipeRest.setIngredient(ingredientRest);

        mockRecipeRest = new RecipeRest();
        mockRecipeRest.setId(1L);
        mockRecipeRest.setInstructions("Test Instructions");
        mockRecipeRest.setName("Test RecipeRest");
        mockRecipeRest.setTags(Set.of("vegetarian", "gluten free"));
        mockRecipeRest.setIngredients(Set.of(ingredientRecipeRest));
    }
}
