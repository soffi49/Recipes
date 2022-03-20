package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Sql({"/data/drop-db.sql", "/data/create-db.sql", "/data/insert-1-ingredient.sql"})
class IngredientServiceIntegrationTest extends AbstractIntegrationTestConfig {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    @DisplayName("Add ingredient with all data")
    void addIngredientWithAllData() {
        final Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(0);
        ingredient.setName("Test Name");
        ingredient.setPhoto("Test Photo");

        assertThatNoException().isThrownBy(() -> ingredientService.addIngredient(ingredient));

        final List<IngredientDTO> databaseIngredients = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(databaseIngredients).anyMatch(el -> el.getName().equals("Test Name"));
    }

    @Test
    @DisplayName("Add ingredient without photo")
    void addIngredientWithoutPhoto() {
        final Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(0);
        ingredient.setName("Test Name");

        assertThatNoException().isThrownBy(() -> ingredientService.addIngredient(ingredient));

        final List<IngredientDTO> databaseIngredients = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(databaseIngredients).anyMatch(el -> el.getName().equals("Test Name"));
    }

    @Test
    @DisplayName("Add ingredient duplicate")
    void addIngredientDuplicate() {
        final Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(0);
        ingredient.setName("Name");

        assertThatThrownBy(() -> ingredientService.addIngredient(ingredient))
                .isExactlyInstanceOf(IngredientDuplicateException.class)
                .hasMessage("Ingredient with name: Name does exist in database");
    }

}
