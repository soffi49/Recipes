package com.recipes.backend.rest;

import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @MockBean
    private static IngredientService ingredientServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Correct ingredient provided")
    void addIngredientWithCorrectData() throws Exception {
        final Ingredient correctIngredient = new Ingredient();
        correctIngredient.setIngredientId(0);
        correctIngredient.setName("Name");
        correctIngredient.setPhoto("Photo");

        Mockito.doNothing().when(ingredientServiceMock).addIngredient(correctIngredient);

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : \"0\", \"name\" : \"Name\", \"photo\" : \"Photo\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Duplicated ingredient provided")
    void addIngredientWithDuplicatedData() throws Exception {
        final Ingredient correctIngredient = new Ingredient();
        correctIngredient.setIngredientId(0);
        correctIngredient.setName("Duplicate");

        Mockito.doThrow(IngredientDuplicateException.class).when(ingredientServiceMock).addIngredient(correctIngredient);

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : \"0\", \"name\" : \"Duplicate\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("The object already exists in the database"));
    }

    @Test
    @DisplayName("Ingredient without name provided")
    void addIngredientWithoutName() throws Exception {
        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"photo\" : \"test\"}"))
                .andExpect(status().isBadRequest());
    }
}
