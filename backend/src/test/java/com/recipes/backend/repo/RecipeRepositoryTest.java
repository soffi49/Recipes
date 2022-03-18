package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractTestConfig;
import com.recipes.backend.repo.domain.RecipeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeRepositoryTest extends AbstractTestConfig {

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
        assertThat(savedRecipe.getRecipeId()).isEqualTo(1);
    }

    private void setUpRecipeDTO() {
        recipeDTO = new RecipeDTO();
        recipeDTO.setInstructions("Test instructions");
    }
}
