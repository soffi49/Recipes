package com.recipes.backend.rest;

import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
class IngredientControllerTest {

    @MockBean
    private static IngredientService ingredientServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save ingredient - correct ingredient provided")
    void addIngredientWithCorrectData() throws Exception {
        final Ingredient correctIngredient = new Ingredient();
        correctIngredient.setIngredientId(0);
        correctIngredient.setName("Name");

        Mockito.doNothing().when(ingredientServiceMock).addIngredient(correctIngredient);

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\" : \"0\", \"name\" : \"Name\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Save ingredient - duplicated ingredient provided")
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
    @DisplayName("Save ingredient - ingredient without name provided")
    void addIngredientWithoutName() throws Exception {
        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"photo\" : \"test\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get all ingredients - correct parameters")
    void getAllIngredientsCorrectParam() throws Exception {
        Mockito.doReturn(setUpIngredientSet()).when(ingredientServiceMock).getAllIngredients(0, 5);

        mockMvc.perform(get("/ingredients")
                        .param("limit", "5")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients", hasSize(3)))
                .andExpect(jsonPath("$.ingredients[0].name", is("Name0")));
    }

    @Test
    @DisplayName("Get all ingredients - incorrect parameters")
    void getAllIngredientsIncorrectParam() throws Exception {
        Mockito.doReturn(setUpIngredientSet()).when(ingredientServiceMock).getAllIngredients(0, 5);

        mockMvc.perform(get("/ingredients")
                        .param("limit", "5")
                        .param("other", "0"))
                .andExpect(status().isBadRequest());
    }

    private Set<Ingredient> setUpIngredientSet() {
        final Set<Ingredient> ingredientRestSet = new HashSet<>();

        LongStream.range(0, 3).forEach(val -> {
            final Ingredient ingredient = new Ingredient();
            ingredient.setIngredientId(val);
            ingredient.setName("Name" + val);
            ingredientRestSet.add(ingredient);
        });

        return ingredientRestSet;
    }
}
