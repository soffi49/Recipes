package com.recipes.backend.rest;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.recipes.backend.rest.domain.IngredientRecipeRest;
import com.recipes.backend.rest.domain.IngredientRest;
import com.recipes.backend.rest.domain.RecipeRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
class RecipeControllerIntegrationTest extends AbstractControllerIntegrationTest
{

    @Test
    @DisplayName("Add recipe with correct body")
    @Sql({"/data/truncate-db.sql", "/data/recipe/insert-1-recipe.sql"})
    @Order(1)
    void shouldAddRecipeGivenCorrectBody() throws JsonProcessingException
    {
        // given
        var recipeRest = new RecipeRest(1L,
                                        "TEST_NAME",
                                        "TEST_INSTRUCTION",
                                        Set.of(new IngredientRecipeRest(new IngredientRest(1000L, "Ingredient"), "10g")), Set.of("vegetarian"));
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(recipeRest), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(createURLWithPort("/recipes"), HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Add recipe with incorrect body")
    @Order(2)
    void shouldNotAddRecipeGivenIncorrectBody() throws JsonProcessingException
    {
        // given
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        var recipeRest = new RecipeRest(1000L,
                                        "TEST_NAME",
                                        "TEST_INSTRUCTION",
                                        Set.of(new IngredientRecipeRest(new IngredientRest(1000L, "Ingredient"), "10g")), null);
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(recipeRest), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(createURLWithPort("/recipes"), HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
