package com.recipes.backend.mapper;

import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.repo.domain.IngredientDTO;
import com.recipes.backend.rest.domain.IngredientRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class IngredientMapperTest {

    private Ingredient mockIngredient;
    private IngredientRest mockIngredientRest;
    private IngredientDTO mockIngredientDTO;

    @BeforeEach
    public void setUp() {
        mockIngredient = new Ingredient();
        mockIngredient.setIngredientId(0);
        mockIngredient.setName("Test Ingredient");

        mockIngredientRest = new IngredientRest();
        mockIngredientRest.setId(0L);
        mockIngredientRest.setName("Test IngredientRest");

        mockIngredientDTO = new IngredientDTO();
        mockIngredientDTO.setIngredientId(1);
        mockIngredientDTO.setName("Test IngredientDTO");
    }

    @Test
    @DisplayName("Map to ingredient from ingredientRest all data")
    void mapToIngredientFromIngredientRestAllData() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient(mockIngredientRest);

        Assertions.assertTrue(retrievedIngredient.isPresent());
        Assertions.assertEquals("Test IngredientRest", retrievedIngredient.get().getName());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientRest null")
    void mapToIngredientFromIngredientRestNull() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient((IngredientRest) null);

        Assertions.assertFalse(retrievedIngredient.isPresent());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientDTO all data")
    void mapToIngredientFromIngredientDTOAllData() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient(mockIngredientDTO);

        Assertions.assertTrue(retrievedIngredient.isPresent());
        Assertions.assertEquals("Test IngredientDTO", retrievedIngredient.get().getName());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientDTO null")
    void mapToIngredientFromIngredientDTONull() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient((IngredientDTO) null);

        Assertions.assertFalse(retrievedIngredient.isPresent());
    }

    @Test
    @DisplayName("Map to ingredientDTO from ingredient all data")
    void mapToIngredientDTOFromIngredientAllData() {
        final Optional<IngredientDTO> retrievedIngredientDTO = IngredientMapper.mapToIngredientDTO(mockIngredient);

        Assertions.assertTrue(retrievedIngredientDTO.isPresent());
        Assertions.assertEquals("Test Ingredient", retrievedIngredientDTO.get().getName());
    }

    @Test
    @DisplayName("Map to ingredientDTO from ingredient null")
    void mapToIngredientDTOFromIngredientNull() {
        final Optional<IngredientDTO> retrievedIngredientDTO = IngredientMapper.mapToIngredientDTO((Ingredient) null);

        Assertions.assertFalse(retrievedIngredientDTO.isPresent());
    }

    @Test
    @DisplayName("Map to ingredientRest from ingredient all data")
    void mapToIngredientRestFromIngredientAllData() {
        final Optional<IngredientRest> retrievedIngredientRest = IngredientMapper.mapToIngredientRest(mockIngredient);

        Assertions.assertTrue(retrievedIngredientRest.isPresent());
        Assertions.assertEquals("Test Ingredient", retrievedIngredientRest.get().getName());
    }

    @Test
    @DisplayName("Map to ingredientRest from ingredient null")
    void mapToIngredientRestFromIngredientNull() {
        final Optional<IngredientRest> retrievedIngredientRest = IngredientMapper.mapToIngredientRest((Ingredient) null);

        Assertions.assertFalse(retrievedIngredientRest.isPresent());
    }
}
