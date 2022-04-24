package com.recipes.backend.rest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipes.backend.bizz.recipe.RecipeService;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.recipe.domain.RecipeTagEnum;
import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.rest.domain.IngredientRecipeRest;
import com.recipes.backend.rest.domain.IngredientRest;
import com.recipes.backend.rest.domain.RecipeRest;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipesController.class)
class RecipeControllerUnitTest
{

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @MockBean
    private static RecipeService recipeServiceMock;
    @MockBean
    private static SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get all recipes - correct parameters and without filters")
    void getAllRecipesCorrectParamNoFilters() throws Exception
    {
        Mockito.doReturn(setUpRecipeSet()).when(recipeServiceMock).getAllRecipes(0, 5, null, null);
        when(securityService.isAuthenticated(any())).thenReturn(true);

        mockMvc.perform(get("/recipes")
                                .param("limit", "5")
                                .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes", hasSize(3)))
                .andExpect(jsonPath("$.recipes[0].name", is("Recipe0")));
    }

    @Test
    @DisplayName("Get all recipes - correct parameters and name filter")
    void getAllRecipesCorrectParamWithNameFilter() throws Exception
    {
        final Recipe recipe = new Recipe();
        recipe.setRecipeId(1L);
        recipe.setName("Recipe1");
        recipe.setInstructions("Some Instructions");

        Mockito.doReturn(Set.of(recipe)).when(recipeServiceMock).getAllRecipes(0, 5, "Recipe1", null);
        when(securityService.isAuthenticated(any())).thenReturn(true);

        mockMvc.perform(get("/recipes")
                                .param("limit", "5")
                                .param("page", "0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\" : \"Recipe1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes", hasSize(1)))
                .andExpect(jsonPath("$.recipes[0].name", is("Recipe1")));
    }

    @Test
    @DisplayName("Get all recipes - correct parameters and tag filter")
    void getAllRecipesCorrectParamWithTagFilter() throws Exception
    {
        final Recipe recipe = new Recipe();
        recipe.setRecipeId(1L);
        recipe.setName("Recipe1");
        recipe.setTags(Set.of(RecipeTagEnum.LOW_CALORIE));
        recipe.setInstructions("Some Instructions");

        Mockito.doReturn(Set.of(recipe)).when(recipeServiceMock).getAllRecipes(0, 5, null, Set.of("low calorie"));
        when(securityService.isAuthenticated(any())).thenReturn(true);

        mockMvc.perform(get("/recipes")
                                .param("limit", "5")
                                .param("page", "0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"tags\" : [\"low calorie\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes", hasSize(1)))
                .andExpect(jsonPath("$.recipes[0].name", is("Recipe1")));
    }

    @Test
    @DisplayName("Get all recipes - incorrect parameters and without filters")
    void getAllRecipesIncorrectParamNoFilters() throws Exception
    {
        Mockito.doReturn(setUpRecipeSet()).when(recipeServiceMock).getAllRecipes(0, 5, null, null);
        when(securityService.isAuthenticated(any())).thenReturn(true);

        mockMvc.perform(get("/recipes")
                                .param("limit", "5")
                                .param("other", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should add recipe - correct parameters")
    void addRecipeCorrectParam() throws Exception
    {
        // given
        var recipeRest = new RecipeRest(1L, "NAME", "INSTRUCTION",
                                        Set.of(new IngredientRecipeRest(new IngredientRest(1L, "INGREDIENT"), "QUANTITY")),
                                        Set.of("vegetarian"));
        when(securityService.isAuthenticated(any())).thenReturn(true);

        mockMvc.perform(post("/recipes")
                                .contentType(APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(recipeRest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should not add recipe - incorrect parameters")
    void addRecipeIncorrectParam() throws Exception
    {
        // given
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        var recipeRest = new RecipeRest(1L, null, null, null, null);
        when(securityService.isAuthenticated(any())).thenReturn(true);

        mockMvc.perform(post("/recipes")
                                .contentType(APPLICATION_JSON)
                                .content(MAPPER.writeValueAsString(recipeRest)))
                .andExpect(status().isBadRequest());
    }


    private Set<Recipe> setUpRecipeSet()
    {
        final Set<Recipe> recipeSet = new HashSet<>();

        LongStream.range(0, 3).forEach(val -> {
            final Recipe recipe = new Recipe();
            recipe.setRecipeId(val);
            recipe.setName("Recipe" + val);
            recipe.setInstructions("Some Instructions");
            recipeSet.add(recipe);
        });

        return recipeSet;
    }
}
