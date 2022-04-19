package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Transactional
class RecipeIngredientRepositoryTest extends AbstractIntegrationTestConfig
{
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Test
    @DisplayName("Find all non empty")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-2-recipe-ingredient.sql"})
    @Order(1)
    void findAllNonEmpty()
    {
        final List<RecipeIngredientDTO> result = (List<RecipeIngredientDTO>) recipeIngredientRepository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.stream()).anyMatch(recipe -> recipe.getRecipe().getName().equals("Recipe"));
    }

    @Test
    @DisplayName("Find all empty")
    @Sql("/data/truncate-db.sql")
    @Order(2)
    void findAllEmpty()
    {
        final List<RecipeIngredientDTO> result = (List<RecipeIngredientDTO>) recipeIngredientRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Delete all by recipeId non empty")
    @Sql("/data/recipe/insert-2-recipe-ingredient.sql")
    @Order(3)
    void deleteAllByRecipeIdNonEmpty()
    {
        recipeIngredientRepository.deleteAllByRecipeId(1000L);
        final List<RecipeIngredientDTO> result = (List<RecipeIngredientDTO>) recipeIngredientRepository.findAll();

        assertThat(result.stream().filter(recipe -> recipe.getRecipe().getRecipeId() == 1000L)).isEmpty();
    }

    @Test
    @DisplayName("Delete all by recipeId empty")
    @Order(4)
    void deleteAllByRecipeIdEmpty()
    {
        assertDoesNotThrow(() -> recipeIngredientRepository.deleteAllByRecipeId(1000L));
    }

    @Test
    @DisplayName("Add ingredient for recipe correct data")
    @Sql("/data/recipe/insert-2-recipe-ingredient.sql")
    @Order(5)
    void addIngredientForRecipeCorrectData()
    {
        recipeIngredientRepository.deleteAllByRecipeId(1000L);

        final long recipeId = 1000L;
        final long ingredientId = 1000L;
        final String quantity = "10g";

        final boolean doesHaveBefore = ((List<RecipeIngredientDTO>) recipeIngredientRepository.findAll()).stream()
                .anyMatch(ig -> ig.getIngredient().getIngredientId() == ingredientId && ig.getRecipe().getRecipeId() == recipeId);

        recipeIngredientRepository.addIngredientForRecipe(recipeId, ingredientId, quantity);

        final Optional<RecipeIngredientDTO> addedIngredient = ((List<RecipeIngredientDTO>) recipeIngredientRepository.findAll()).stream()
                .filter(ig -> ig.getRecipe().getRecipeId() == recipeId && ig.getIngredient().getIngredientId() == ingredientId)
                .findFirst();

        assertThat(doesHaveBefore).isFalse();
        assertThat(addedIngredient).isPresent();
        assertThat(addedIngredient.get().getIngredient().getName()).isEqualTo("Ingredient1");
        assertThat(addedIngredient.get().getRecipe().getName()).isEqualTo("Recipe");
    }

    @ParameterizedTest
    @DisplayName("Add ingredient for recipe non existing recipe or ingredient or duplicate")
    @Order(6)
    @MethodSource("provideIncorrectIngredientRecipeData")
    void addIngredientForRecipeWrongData(final long recipeId, final long ingredientId, final String quantity)
    {
        assertThatThrownBy(() -> recipeIngredientRepository.addIngredientForRecipe(recipeId, ingredientId, quantity))
                .isExactlyInstanceOf(DataIntegrityViolationException.class)
                .hasRootCauseInstanceOf(SQLIntegrityConstraintViolationException.class);
    }

    private static Stream<Arguments> provideIncorrectIngredientRecipeData()
    {
        return Stream.of(
                Arguments.of(10000L, 1000L, "quantity"),
                Arguments.of(1000L, 10000L, "quantity"),
                Arguments.of(1000L, 1000L, "quantity")
                        );
    }

}
