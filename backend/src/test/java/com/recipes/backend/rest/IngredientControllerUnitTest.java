package com.recipes.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.backend.bizz.ingredient.IngredientService;
import com.recipes.backend.bizz.ingredient.domain.Ingredient;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import com.recipes.backend.exception.domain.IngredientNotFound;
import com.recipes.backend.rest.domain.IngredientRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(IngredientController.class)
class IngredientControllerUnitTest
{

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Long INGREDIENT_ID = 1L;
    private static final String INGREDIENT_NAME = "TEST_NAME";
    private static final String INGREDIENT_QUANTITY = "QUANTITY";

    @MockBean
    private static IngredientService ingredientService;
    @MockBean
    private static SecurityService securityService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init()
    {
        lenient().when(securityService.isAuthenticated(any())).thenReturn(true);
    }

    @Test
    @DisplayName("Save ingredient - correct ingredient provided")
    void addIngredientWithCorrectData() throws Exception
    {
        final Ingredient correctIngredient = new Ingredient();
        correctIngredient.setIngredientId(0);
        correctIngredient.setName("Name");

        doNothing().when(ingredientService).addIngredient(correctIngredient);

        mockMvc.perform(post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\" : \"0\", \"name\" : \"Name\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Save ingredient - duplicated ingredient provided")
    void addIngredientWithDuplicatedData() throws Exception
    {
        final Ingredient correctIngredient = new Ingredient();
        correctIngredient.setIngredientId(0);
        correctIngredient.setName("Duplicate");

        Mockito.doThrow(IngredientDuplicateException.class).when(ingredientService).addIngredient(correctIngredient);

        mockMvc.perform(post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\" : \"0\", \"name\" : \"Duplicate\"}"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("The object already exists in the database"));
    }

    @Test
    @DisplayName("Save ingredient - ingredient without name provided")
    void addIngredientWithoutName() throws Exception
    {
        mockMvc.perform(post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"photo\" : \"test\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get all ingredients - correct parameters without filters")
    void getAllIngredientsCorrectParamWithoutFilters() throws Exception
    {
        Mockito.doReturn(setUpIngredientSet()).when(ingredientService).getAllIngredients(0, 5, null);

        mockMvc.perform(get("/ingredients")
                                .param("limit", "5")
                                .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients", hasSize(3)))
                .andExpect(jsonPath("$.ingredients[0].name", is("Name0")));
    }

    @Test
    @DisplayName("Get all ingredients - incorrect parameters without filters")
    void getAllIngredientsIncorrectParamWithoutFilters() throws Exception
    {
        Mockito.doReturn(setUpIngredientSet()).when(ingredientService).getAllIngredients(0, 5, null);

        mockMvc.perform(get("/ingredients")
                                .param("limit", "5")
                                .param("other", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get all ingredients - incorrect parameters with filter")
    void getAllIngredientsIncorrectParamWithFilters() throws Exception
    {
        final Set<Ingredient> resultSet = Set.of(setUpIngredientSet().stream().findFirst().get());

        Mockito.doReturn(resultSet).when(ingredientService).getAllIngredients(0, 5, "Name0");

        mockMvc.perform(get("/ingredients")
                                .param("limit", "5")
                                .param("page", "0")
                                .param("name", "Name0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients", hasSize(1)));
    }

    @Test
    @DisplayName("Delete ingredients - correct data")
    void deleteIngredientCorrectIngredient() throws Exception
    {
        Mockito.doReturn(true).when(ingredientService).deleteIngredient(INGREDIENT_ID);

        mockMvc.perform(delete("/ingredients/{id}", INGREDIENT_ID))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete ingredients - not found ingredient")
    void deleteIngredientNotFound() throws Exception
    {
        Mockito.doThrow(new IngredientNotFound(INGREDIENT_ID)).when(ingredientService).deleteIngredient(INGREDIENT_ID);

        mockMvc.perform(delete("/ingredients/{id}", INGREDIENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should correctly update ingredient given correct params")
    void shouldCorrectlyUpdateIngredientGivenCorrectParams() throws Exception
    {
        // given
        var ingredientRest = new IngredientRest(INGREDIENT_ID, INGREDIENT_NAME);
        var savedIngredient = new Ingredient(INGREDIENT_ID, INGREDIENT_NAME, INGREDIENT_QUANTITY);

        doNothing().when(ingredientService).updateIngredient(any(Ingredient.class));

        // when
        mockMvc.perform(put("/ingredients/{id}", INGREDIENT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(ingredientRest)))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return forbidden code given incorrect param")
    void shouldReturnForbiddenCodeGivenIncorrectParam() throws Exception
    {
        // given
        var ingredientRest = new IngredientRest(INGREDIENT_ID, INGREDIENT_NAME);

        doThrow(IngredientEmptyException.class).when(ingredientService).updateIngredient(any(Ingredient.class));

        // when
        mockMvc.perform(put("/ingredients/{id}", INGREDIENT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(ingredientRest)))
                // then
                .andExpect(status().isForbidden());
    }

    private Set<Ingredient> setUpIngredientSet()
    {
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
