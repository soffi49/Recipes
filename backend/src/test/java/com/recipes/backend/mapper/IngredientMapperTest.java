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
        mockIngredient.setPhoto("Test Photo");

        mockIngredientRest = new IngredientRest();
        mockIngredientRest.setId(0L);
        mockIngredientRest.setName("Test IngredientRest");
        mockIngredientRest.setPhoto("Test PhotoRest");

        mockIngredientDTO = new IngredientDTO();
        mockIngredientDTO.setIngredientId(1);
        mockIngredientDTO.setName("Test IngredientDTO");
        mockIngredientDTO.setPhoto("Test PhotoDTO");
    }

    @Test
    @DisplayName("Map to ingredient from ingredientRest not null with photo")
    void mapToIngredientFromIngredientRestNotNullPhoto() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient(mockIngredientRest);

        Assertions.assertTrue(retrievedIngredient.isPresent());
        Assertions.assertEquals(0, retrievedIngredient.get().getIngredientId());
        Assertions.assertEquals("Test PhotoRest", retrievedIngredient.get().getPhoto());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientRest not null without photo")
    void mapToIngredientFromIngredientRestNotNul() {
        mockIngredientRest.setPhoto(null);
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient(mockIngredientRest);

        Assertions.assertTrue(retrievedIngredient.isPresent());
        Assertions.assertNull(retrievedIngredient.get().getPhoto());
        Assertions.assertEquals("Test IngredientRest", retrievedIngredient.get().getName());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientRest null")
    void mapToIngredientFromIngredientRestNull() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient((IngredientRest) null);

        Assertions.assertFalse(retrievedIngredient.isPresent());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientDTO not null with photo")
    void mapToIngredientFromIngredientDTONotNullPhoto() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient(mockIngredientDTO);

        Assertions.assertTrue(retrievedIngredient.isPresent());
        Assertions.assertEquals(1, retrievedIngredient.get().getIngredientId());
        Assertions.assertEquals("Test PhotoDTO", retrievedIngredient.get().getPhoto());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientDTO not null without photo")
    void mapToIngredientFromIngredientDTONotNul() {
        mockIngredientDTO.setPhoto(null);
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient(mockIngredientDTO);

        Assertions.assertTrue(retrievedIngredient.isPresent());
        Assertions.assertNull(retrievedIngredient.get().getPhoto());
        Assertions.assertEquals("Test IngredientDTO", retrievedIngredient.get().getName());
    }

    @Test
    @DisplayName("Map to ingredient from ingredientDTO null")
    void mapToIngredientFromIngredientDTONull() {
        final Optional<Ingredient> retrievedIngredient = IngredientMapper.mapToIngredient((IngredientDTO) null);

        Assertions.assertFalse(retrievedIngredient.isPresent());
    }

    @Test
    @DisplayName("Map to ingredientDTO from ingredient not null with photo")
    void mapToIngredientDTOFromIngredientNotNullPhoto() {
        final Optional<IngredientDTO> retrievedIngredientDTO = IngredientMapper.mapToIngredientDTO(mockIngredient);

        Assertions.assertTrue(retrievedIngredientDTO.isPresent());
        Assertions.assertEquals(0L, retrievedIngredientDTO.get().getIngredientId());
        Assertions.assertEquals("Test Photo", retrievedIngredientDTO.get().getPhoto());
    }

    @Test
    @DisplayName("Map to ingredientDTO from ingredient not null without photo")
    void mapToIngredientDTOFromIngredientNotNul() {
        mockIngredient.setPhoto(null);
        final Optional<IngredientDTO> retrievedIngredientDTO = IngredientMapper.mapToIngredientDTO(mockIngredient);

        Assertions.assertTrue(retrievedIngredientDTO.isPresent());
        Assertions.assertNull(retrievedIngredientDTO.get().getPhoto());
        Assertions.assertEquals("Test Ingredient", retrievedIngredientDTO.get().getName());
    }

    @Test
    @DisplayName("Map to ingredientDTO from ingredient null")
    void mapToIngredientDTOFromIngredientNull() {
        final Optional<IngredientDTO> retrievedIngredientDTO = IngredientMapper.mapToIngredientDTO((Ingredient) null);

        Assertions.assertFalse(retrievedIngredientDTO.isPresent());
    }

    @Test
    @DisplayName("Map to ingredientRest from ingredient not null with photo")
    void mapToIngredientRestFromIngredientNotNullPhoto() {
        final Optional<IngredientRest> retrievedIngredientRest = IngredientMapper.mapToIngredientRest(mockIngredient);

        Assertions.assertTrue(retrievedIngredientRest.isPresent());
        Assertions.assertEquals(0L, retrievedIngredientRest.get().getId());
        Assertions.assertEquals("Test Photo", retrievedIngredientRest.get().getPhoto());
    }

    @Test
    @DisplayName("Map to ingredientRest from ingredient not null without photo")
    void mapToIngredientRestFromIngredientNotNul() {
        mockIngredient.setPhoto(null);
        final Optional<IngredientRest> retrievedIngredientRest = IngredientMapper.mapToIngredientRest(mockIngredient);

        Assertions.assertTrue(retrievedIngredientRest.isPresent());
        Assertions.assertNull(retrievedIngredientRest.get().getPhoto());
        Assertions.assertEquals("Test Ingredient", retrievedIngredientRest.get().getName());
    }

    @Test
    @DisplayName("Map to ingredientRest from ingredient null")
    void mapToIngredientRestFromIngredientNull() {
        final Optional<IngredientRest> retrievedIngredientRest = IngredientMapper.mapToIngredientRest((Ingredient) null);

        Assertions.assertFalse(retrievedIngredientRest.isPresent());
    }
}
