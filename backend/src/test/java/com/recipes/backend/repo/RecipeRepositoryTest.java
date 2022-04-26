package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RecipeRepositoryTest extends AbstractIntegrationTestConfig
{

    @Autowired
    private RecipeRepository recipeRepository;

    private RecipeDTO mockRecipeDTO;

    @BeforeEach
    public void setUp()
    {
        setUpRecipeDTO();
    }

    @Test
    @DisplayName("Save non empty recipe")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-1-recipe.sql"})
    @Order(1)
    void saveRecipeNotEmpty()
    {
        mockRecipeDTO.setName("Test name");

        final RecipeDTO savedRecipe = recipeRepository.save(mockRecipeDTO);
        assertThat(savedRecipe).usingRecursiveComparison().isEqualTo(mockRecipeDTO);
    }

    @Test
    @DisplayName("Save recipe with empty name")
    @Order(2)
    void saveRecipeWithoutName()
    {
        assertThatThrownBy(() -> recipeRepository.save(mockRecipeDTO))
                .hasRootCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Save recipe duplicate")
    @Order(3)
    void saveRecipeDuplicate()
    {
        mockRecipeDTO.setName("Recipe");

        assertThatThrownBy(() -> recipeRepository.save(mockRecipeDTO))
                .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Find recipe by id when present")
    @Order(4)
    void findByIdPresent()
    {
        final Optional<RecipeDTO> retrieverRecipe = recipeRepository.findById(1000L);

        assertThat(retrieverRecipe).isPresent();
        assertThat(retrieverRecipe.get().getName()).isEqualTo("Recipe");
    }

    @Test
    @DisplayName("Find recipe by id when not present")
    @Order(5)
    void findByIdNotPresent()
    {
        final Optional<RecipeDTO> retrievedRecipe = recipeRepository.findById(4000L);

        assertThat(retrievedRecipe).isEmpty();
    }

    @Test
    @DisplayName("Find all recipes not empty")
    @Order(6)
    void findAllRecipesNonEmpty()
    {
        final List<RecipeDTO> databaseRecipes = (List<RecipeDTO>) recipeRepository.findAll();

        assertThat(databaseRecipes).hasSize(2);
        assertThat(databaseRecipes.stream()).anyMatch(recipe -> recipe.getName().equals("Test name"));
    }

    @Test
    @DisplayName("Find all recipes empty")
    @Sql("/data/truncate-db.sql")
    @Order(7)
    void findAllRecipesEmpty()
    {
        final List<RecipeDTO> databaseRecipes = (List<RecipeDTO>) recipeRepository.findAll();

        assertThat(databaseRecipes).isEmpty();
    }

    @Test
    @DisplayName("Delete existing recipe by id")
    @Sql("/data/recipe/insert-1-recipe.sql")
    @Order(8)
    void deleteByIdExistingRecipe()
    {
        recipeRepository.deleteById(1000L);

        assertThat(recipeRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Delete non existing recipe by id")
    @Order(9)
    void deleteByIdNonExistingRecipe()
    {
        assertThatThrownBy(() -> recipeRepository.deleteById(1000L))
                .isExactlyInstanceOf(EmptyResultDataAccessException.class)
                .hasMessage("No class com.recipes.backend.repo.domain.RecipeDTO entity with id 1000 exists!");
    }

    private void setUpRecipeDTO()
    {
        mockRecipeDTO = new RecipeDTO();
        mockRecipeDTO.setInstructions("Test instructions");
    }
}
