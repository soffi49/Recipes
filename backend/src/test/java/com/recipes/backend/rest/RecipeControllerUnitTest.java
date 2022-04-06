package com.recipes.backend.rest;

import com.recipes.backend.bizz.recipe.RecipeService;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.bizz.security.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipesController.class)
public class RecipeControllerUnitTest {

    @MockBean
    private static RecipeService recipeServiceMock;

    @MockBean
    private static SecurityService securityServiceMock;

    private static String SECURITY_HEADER = "Header Mock";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get all recipes - correct parameters")
    void getAllRecipesCorrectParam() throws Exception {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("security_header", SECURITY_HEADER);

        Mockito.doReturn(setUpRecipeSet()).when(recipeServiceMock).getAllRecipes(0, 5);
        Mockito.doReturn(true).when(securityServiceMock).isAuthenticated(httpHeaders);

        mockMvc.perform(get("/recipes")
                .headers(httpHeaders)
                .param("limit", "5")
                .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes", hasSize(3)))
                .andExpect(jsonPath("$.recipes[0].name", is("Recipe0")));
    }

    @Test
    @DisplayName("Get all recipes - incorrect parameters")
    void getAllRecipesIncorrectParam() throws Exception {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("security_header", SECURITY_HEADER);

        Mockito.doReturn(setUpRecipeSet()).when(recipeServiceMock).getAllRecipes(0, 5);
        Mockito.doReturn(true).when(securityServiceMock).isAuthenticated(httpHeaders);

        mockMvc.perform(get("/recipes").headers(httpHeaders)
                .param("limit", "5")
                .param("other", "0"))
                .andExpect(status().isBadRequest());
    }

    private Set<Recipe> setUpRecipeSet() {
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
