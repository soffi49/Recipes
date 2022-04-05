package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.repo.domain.RecipeDTO;
import com.recipes.backend.repo.domain.RecipeIngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Sql({"/data/drop-db.sql","/data/create-db.sql", "/data/recipe/insert-1-recipe.sql"})
public class RecipeRepositoryTest extends AbstractIntegrationTestConfig {

    @Autowired
    private RecipeRepository recipeRepository;

    private RecipeDTO mockRecipeDTO;

    @BeforeEach
    public void setUp() {
        setUpRecipeDTO();
    }

    @Test
    @DisplayName("Save non empty recipe")
    void saveRecipeNotEmpty() {
        mockRecipeDTO.setName("Test name");

        final RecipeDTO savedRecipe = recipeRepository.save(mockRecipeDTO);
        assertThat(savedRecipe).usingRecursiveComparison().isEqualTo(mockRecipeDTO);
    }

    @Test
    @DisplayName("Save recipe with empty name")
    void saveRecipeWithoutName() {
        assertThatThrownBy(() -> recipeRepository.save(mockRecipeDTO))
                .hasRootCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save recipe duplicate")
    void saveRecipeDuplicate() {
        mockRecipeDTO.setName("Recipe");

        assertThatThrownBy(() -> recipeRepository.save(mockRecipeDTO))
                .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Find recipe by id when present")
    void findByIdPresent() {
        final Optional<RecipeDTO> retrieverRecipe = recipeRepository.findById(1000L);

        assertThat(retrieverRecipe).isPresent();
        assertThat(retrieverRecipe.get().getName()).isEqualTo("Recipe");
    }

    @Test
    @DisplayName("Find recipe by id when not present")
    void findByIdNotPresent() {
        final Optional<RecipeDTO> retrievedRecipe = recipeRepository.findById(4000L);

        assertThat(retrievedRecipe).isEmpty();
    }

    @Test
    @DisplayName("Find all recipes not empty")
    @Sql("/data/recipe/insert-1-recipe.sql")
    void findAllIngredients() {
        final List<RecipeDTO> databaseRecipes = (List<RecipeDTO>) recipeRepository.findAll();

        assertThat(databaseRecipes.size()).isEqualTo(1);
        assertThat(databaseRecipes.get(0).getName()).isEqualTo("Recipe");
    }

    private void setUpRecipeDTO() {
        mockRecipeDTO = new RecipeDTO();
        mockRecipeDTO.setInstructions("Test instructions");
    }
}
