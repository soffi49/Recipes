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
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Sql({"/data/drop-db-if-exists.sql", "/data/create-db.sql", "/data/insert-1-ingredient.sql"})
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

    @Test
    @DisplayName("Get all ingredients one page")
    @Sql({"/data/truncate-ingredients.sql", "/data/insert-5-ingredients.sql"})
    void getAllIngredientsOnePage() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit);
        final Set<String> ingredientsNames = retrievedList.stream().map(Ingredient::getName).collect(Collectors.toSet());

        assertThat(retrievedList.size()).isEqualTo(5);
        assertThat(ingredientsNames).contains("Name4");
    }

    @Test
    @DisplayName("Get all ingredients more pages")
    @Sql({"/data/truncate-ingredients.sql", "/data/insert-5-ingredients.sql"})
    void getAllIngredientsMorePages() {
        final int limit = 3;

        final Set<Ingredient> retrievedList1 = ingredientService.getAllIngredients(0, limit);
        final Set<Ingredient> retrievedList2 = ingredientService.getAllIngredients(1, limit);

        assertThat(retrievedList1.size()).isEqualTo(3);
        assertThat(retrievedList2.size()).isEqualTo(2);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Name4"))).isFalse();
    }

    @Test
    @DisplayName("Get all ingredients empty")
    @Sql({"/data/create-db.sql", "/data/truncate-ingredients.sql"})
    void getAllIngredientsEmpty() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit);

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Count ingredients non empty")
    @Sql({"/data/truncate-ingredients.sql", "/data/insert-5-ingredients.sql"})
    void countIngredientsNonEmpty() {
        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isEqualTo(5);
    }

    @Test
    @DisplayName("Count ingredients empty")
    @Sql({"/data/create-db.sql", "/data/truncate-ingredients.sql"})
    void countIngredientsEmpty() {
        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isZero();
    }

}
