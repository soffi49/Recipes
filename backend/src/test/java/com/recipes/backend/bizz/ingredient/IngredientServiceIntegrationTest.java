package com.recipes.backend.bizz.ingredient;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.exception.domain.IngredientNotFound;
import com.recipes.backend.repo.IngredientRepository;
import com.recipes.backend.repo.domain.IngredientDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IngredientServiceIntegrationTest extends AbstractIntegrationTestConfig
{

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    @DisplayName("Add ingredient with all data")
    @Sql({"/data/truncate-db.sql", "/data/ingredient/insert-1-ingredient.sql"})
    @Order(1)
    void addIngredientWithAllData()
    {
        final Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(0);
        ingredient.setName("Test Name");

        assertThatNoException().isThrownBy(() -> ingredientService.addIngredient(ingredient));

        final List<IngredientDTO> databaseIngredients = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(databaseIngredients).anyMatch(el -> el.getName().equals("Test Name"));
    }

    @Test
    @DisplayName("Add ingredient duplicate")
    @Order(2)
    void addIngredientDuplicate()
    {
        final Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1000);
        ingredient.setName("Name");

        assertThatThrownBy(() -> ingredientService.addIngredient(ingredient))
                .isExactlyInstanceOf(IngredientDuplicateException.class)
                .hasMessage("Ingredient with name: Name does exist in database");
    }

    @Test
    @DisplayName("Is ingredient present true")
    @Order(4)
    void isIngredientPresentTrue()
    {
        final boolean result = ingredientService.isIngredientPresent(1000L);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Is ingredient present false")
    @Sql({"/data/truncate-db.sql"})
    @Order(5)
    void isIngredientPresentFalse()
    {
        final boolean result = ingredientService.isIngredientPresent(1000L);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Get all ingredients one page without filters")
    @Sql("/data/ingredient/insert-5-ingredients.sql")
    @Order(6)
    void getAllIngredientsOnePageWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null);
        final Set<String> ingredientsNames = retrievedList.stream().map(Ingredient::getName).collect(Collectors.toSet());

        assertThat(retrievedList).hasSize(5);
        assertThat(ingredientsNames).contains("Name4");
    }

    @Test
    @DisplayName("Get all ingredients more pages without filters")
    @Order(7)
    void getAllIngredientsMorePagesWithoutFilters()
    {
        final int limit = 3;

        final Set<Ingredient> retrievedList1 = ingredientService.getAllIngredients(0, limit, null);
        final Set<Ingredient> retrievedList2 = ingredientService.getAllIngredients(1, limit, null);

        assertThat(retrievedList1).hasSize(3);
        assertThat(retrievedList2).hasSize(2);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Name4"))).isFalse();
    }

    @Test
    @DisplayName("Get all ingredients with existing name")
    @Order(8)
    void getAllIngredientsWithSpecifiedName()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, "Name1");

        assertThat(retrievedList.size()).isOne();
    }

    @Test
    @DisplayName("Get all ingredients with not existing name")
    @Order(9)
    void getAllNotExistingIngredientsWithSpecifiedName()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, "Name10");

        assertThat(retrievedList).isEmpty();
    }

    @Test
    @DisplayName("Get all ingredients empty without filters")
    @Sql("/data/truncate-db.sql")
    @Order(10)
    void getAllIngredientsEmptyWithoutFilters()
    {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null);

        assertThat(retrievedList).isEmpty();
    }

    @Test
    @DisplayName("Count ingredients non empty")
    @Sql("/data/ingredient/insert-5-ingredients.sql")
    @Order(11)
    void countIngredientsNonEmpty()
    {
        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isEqualTo(5);
    }

    @Test
    @DisplayName("Count ingredients empty")
    @Sql("/data/truncate-db.sql")
    @Order(12)
    void countIngredientsEmpty()
    {
        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isZero();
    }

    @Test
    @DisplayName("Delete one existing ingredient")
    @Sql("/data/ingredient/insert-1-ingredient.sql")
    @Order(13)
    void deleteOneProperIngredient()
    {
        assertThat(ingredientService.deleteIngredient((long) 1000)).isTrue();

        final List<IngredientDTO> ingredientList = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(ingredientList).isEmpty();

    }

    @Test
    @DisplayName("Delete one not existing ingredient")
    @Sql("/data/ingredient/insert-1-ingredient.sql")
    @Order(14)
    void deleteOneNotExistingIngredient()
    {

        assertThat(ingredientService.deleteIngredient((long) 1)).isFalse();

        final List<IngredientDTO> ingredientList = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(ingredientList).isNotEmpty();
    }


    @Test
    @DisplayName("Update existing ingredient")
    @Order(15)
    void updateExistingIngredient()
    {
        var ingredient = new Ingredient(1000L, "NEW_NAME", "QUANTITY");

        var ingredientToBeUpdated = ingredientRepository.findById(1000L);
        var updatedIngredient = ingredientService.updateIngredient(ingredient);
        var ingredientAfterUpdate = ingredientRepository.findById(1000L);

        assertThat(ingredientToBeUpdated).isPresent();
        assertThat(ingredientToBeUpdated.get().getName()).isEqualTo("Name");
        assertThat(updatedIngredient.getName()).isEqualTo("NEW_NAME");
        assertThat(updatedIngredient.getIngredientId()).isEqualTo(1000L);
        assertThat(ingredientAfterUpdate).isPresent();
        assertThat(ingredientAfterUpdate.get().getName()).isEqualTo("NEW_NAME");
    }

    @Test
    @DisplayName("Update non existing ingredient")
    @Sql("/data/truncate-db.sql")
    @Order(16)
    void updateNonExistingIngredient()
    {
        var ingredient = new Ingredient(1000L, "NEW_NAME", "QUANTITY");

        assertThatThrownBy(() -> ingredientService.updateIngredient(ingredient))
                .isExactlyInstanceOf(IngredientNotFound.class)
                .hasMessage("Ingredient with the id: 1000 does not exist");
    }
}
