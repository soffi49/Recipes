package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.IngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@Sql({"/data/drop-db.sql", "/data/create-db.sql", "/data/insert-1-ingredient.sql"})
class IngredientRepositoryTest extends AbstractIntegrationTestConfig {

    @Autowired
    private IngredientRepository ingredientRepository;

    private IngredientDTO mockIngredient;

    @BeforeEach
    public void setUp() {
        mockIngredient = new IngredientDTO();
        mockIngredient.setName("Test Ingredient");
        mockIngredient.setPhoto("Test Photo");
    }

    @Test
    @DisplayName("Save ingredient with photo")
    void saveIngredientWithPhoto() {
        final IngredientDTO ingredientDTO = ingredientRepository.save(mockIngredient);

        assertThat(ingredientDTO).usingRecursiveComparison().isEqualTo(mockIngredient);
        assertThat(ingredientDTO.getPhoto()).isEqualTo("Test Photo");
    }

    @Test
    @DisplayName("Save ingredient without photo")
    void saveIngredientWithoutPhoto() {
        mockIngredient.setPhoto(null);
        final IngredientDTO ingredientDTO = ingredientRepository.save(mockIngredient);

        assertThat(ingredientDTO).usingRecursiveComparison().isEqualTo(mockIngredient);
        assertThat(ingredientDTO.getPhoto()).isNull();
        assertThat(ingredientDTO.getIngredientId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save ingredient duplicate")
    void saveIngredientDuplicate() {
        mockIngredient.setName("Name");
        mockIngredient.setPhoto("Photo");

        assertThatThrownBy(() -> ingredientRepository.save(mockIngredient))
                .isExactlyInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Save ingredient with null name")
    void saveIngredientNullName() {
        mockIngredient.setName(null);
        mockIngredient.setPhoto("Photo");

        assertThatThrownBy(() -> ingredientRepository.save(mockIngredient))
                .hasRootCauseInstanceOf(ConstraintViolationException.class);
    }
}
