package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.IngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IngredientRepositoryTest extends AbstractIntegrationTestConfig
{

    @Autowired
    private IngredientRepository ingredientRepository;

    private IngredientDTO mockIngredient;

    @BeforeEach
    public void setUp()
    {
        mockIngredient = new IngredientDTO();
        mockIngredient.setIngredientId(0);
        mockIngredient.setName("Test Ingredient");
    }

    @Test
    @DisplayName("Save ingredient all data")
    @Sql("/data/ingredient/insert-1-ingredient.sql")
    @Order(1)
    void saveIngredientAllData()
    {
        final IngredientDTO ingredientDTO = ingredientRepository.save(mockIngredient);

        assertThat(ingredientDTO).usingRecursiveComparison().isEqualTo(mockIngredient);
        assertThat(ingredientDTO.getName()).isEqualTo("Test Ingredient");
    }

    @Test
    @DisplayName("Save ingredient duplicate")
    void saveIngredientDuplicate()
    {
        mockIngredient.setName("Name");

        assertThatThrownBy(() -> ingredientRepository.save(mockIngredient))
                .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Save ingredient with null name")
    void saveIngredientNullName()
    {
        mockIngredient.setName(null);

        assertThatThrownBy(() -> ingredientRepository.save(mockIngredient))
                .hasRootCauseInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("Find ingredient by id when present")
    void findByIdPresent()
    {
        final Optional<IngredientDTO> retrievedIngredient = ingredientRepository.findById(1000L);

        assertThat(retrievedIngredient).isPresent();
        assertThat(retrievedIngredient.get().getName()).isEqualTo("Name");
    }

    @Test
    @DisplayName("Find ingredient by id when not present")
    void findByIdNotPresent()
    {
        final Optional<IngredientDTO> retrievedIngredient = ingredientRepository.findById(4000L);

        assertThat(retrievedIngredient).isEmpty();
    }

    @Test
    @DisplayName("Find all ingredients not empty")
    @Sql("/data/ingredient/truncate-ingredients.sql")
    void findAllIngredients()
    {
        ingredientRepository.save(mockIngredient);
        final List<IngredientDTO> databaseIngredients = (List<IngredientDTO>) ingredientRepository.findAll();

        assertThat(databaseIngredients.size()).isEqualTo(1);
    }
}
