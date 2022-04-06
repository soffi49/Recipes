package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.exception.domain.MissingQuantityException;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.repo.domain.RecipeDTO;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import com.recipes.backend.repo.domain.TagDTO;
import com.recipes.backend.rest.domain.RecipeRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

class RecipeMapperTest {

    private RecipeDTO mockRecipeDTO;

    private Recipe mockRecipe;

    @BeforeEach
    public void setUp() {
        setUpRecipe();
        setUpRecipeDTO();
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
    @DisplayName("Map to recipeRest from recipe not null")
    void mapToRecipeRectFromRecipeNotNull() {
        mockRecipe.getIngredients().stream().findFirst().get().setQuantity("10g");
        final Optional<RecipeRest> retrievedRecipeRest = RecipeMapper.mapToRecipeRest(mockRecipe);

        Assertions.assertTrue(retrievedRecipeRest.isPresent());
        Assertions.assertEquals(2, retrievedRecipeRest.get().getTags().size());
        Assertions.assertEquals("Test Instructions", retrievedRecipeRest.get().getInstructions());
    }

    @Test
    @DisplayName("Map to recipeRest from recipe quantity null")
    void mapToRecipeRectFromRecipeQuantityNull() {
        assertThatThrownBy(() -> RecipeMapper.mapToRecipeRest(mockRecipe))
                .isExactlyInstanceOf(MissingQuantityException.class)
                .hasMessage("The quantity is missing in the provided ingredient");
    }

    @Test
    @DisplayName("Map to recipeRest from recipe is null")
    void mapToRecipeRectFromRecipeNull() {
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
}
