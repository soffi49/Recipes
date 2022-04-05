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

@Sql({"/data/drop-db-if-exists.sql", "/data/create-db.sql", "/data/ingredient/insert-1-ingredient.sql"})
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
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllIngredientsOnePage() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit,null,null);
        final Set<String> ingredientsNames = retrievedList.stream().map(Ingredient::getName).collect(Collectors.toSet());

        assertThat(retrievedList.size()).isEqualTo(5);
        assertThat(ingredientsNames).contains("Name4");
    }

    @Test
    @DisplayName("Get all ingredients more pages")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllIngredientsMorePages() {
        final int limit = 3;

        final Set<Ingredient> retrievedList1 = ingredientService.getAllIngredients(0, limit, null,null);
        final Set<Ingredient> retrievedList2 = ingredientService.getAllIngredients(1, limit, null,null);

        assertThat(retrievedList1.size()).isEqualTo(3);
        assertThat(retrievedList2.size()).isEqualTo(2);
        assertThat(retrievedList1.stream().anyMatch(el -> el.getName().equals("Name4"))).isFalse();
    }

    @Test
    @DisplayName("Get all ingredients empty")
    @Sql({"/data/create-db.sql", "/data/ingredient/truncate-ingredients.sql"})
    void getAllIngredientsEmpty() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null,null);

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Get all ingredients with existing id")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllIngredientsWithSpecifiedId() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, (long)3,null);

        assertThat(retrievedList.size()).isOne();
    }

    @Test
    @DisplayName("Get all ingredients with not existing id")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllNotExistingIngredientsWithSpecifiedId() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, (long)10,null);

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Get all ingredients with existing name")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllIngredientsWithSpecifiedName() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null,"Name1");

        assertThat(retrievedList.size()).isOne();
    }

    @Test
    @DisplayName("Get all ingredients with not existing name")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllNotExistingIngredientsWithSpecifiedName() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, null,"Name10");

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Get all ingredients with existing name and id")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllIngredientsWithSpecifiedNameAndId() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, (long)1,"Name1");

        assertThat(retrievedList.size()).isOne();
    }

    @Test
    @DisplayName("Get all ingredients with not existing name and id")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void getAllNotExistingIngredientsWithSpecifiedNameAndId() {
        final int limit = 5;
        final int page = 0;

        final Set<Ingredient> retrievedList = ingredientService.getAllIngredients(page, limit, (long)10,"Name10");

        assertThat(retrievedList.size()).isZero();
    }

    @Test
    @DisplayName("Delete one existing ingredient")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-1-ingredient.sql"})
    void deleteOneProperIngredient() {

        assertThat(ingredientService.deleteIngredient((long) 1000)).isTrue();

        final List<IngredientDTO> ingredientList = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(ingredientList).isEmpty();

    }

    @Test
    @DisplayName("Delete one not existing ingredient")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-1-ingredient.sql"})
    void deleteOneNotExistingIngredient() {

        assertThat(ingredientService.deleteIngredient((long) 1)).isFalse();

        final List<IngredientDTO> ingredientList = (List<IngredientDTO>) ingredientRepository.findAll();
        assertThat(ingredientList).isNotEmpty();
    }

    @Test
    @DisplayName("Count ingredients non empty")
    @Sql({"/data/ingredient/truncate-ingredients.sql", "/data/ingredient/insert-5-ingredients.sql"})
    void countIngredientsNonEmpty() {
        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isEqualTo(5);
    }

    @Test
    @DisplayName("Count ingredients empty")
    @Sql({"/data/create-db.sql", "/data/ingredient/truncate-ingredients.sql"})
    void countIngredientsEmpty() {
        final long retrievedList = ingredientService.getIngredientsCount();

        assertThat(retrievedList).isZero();
    }


    @Test
    void getUpdatedIngredient() {
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
}
