package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql({"/data/drop-db.sql","/data/create-db.sql"})
public class RecipeRepositoryTest extends AbstractIntegrationTestConfig {

    @Autowired
    private RecipeRepository recipeRepository;

    private RecipeDTO recipeDTO;

    @BeforeEach
    public void setUp() {
        setUpRecipeDTO();
    }

    @Test
    void saveRecipeNotEmpty() {
        recipeDTO.setName("Test name");

        final RecipeDTO savedRecipe = recipeRepository.save(recipeDTO);
        assertThat(savedRecipe).usingRecursiveComparison().isEqualTo(recipeDTO);
    }

    private void setUpRecipeDTO() {
        recipeDTO = new RecipeDTO();
        recipeDTO.setInstructions("Test instructions");
    }
}
