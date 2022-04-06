package com.recipes.backend.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.recipes.backend.rest.domain.IngredientRecipeRest;
import com.recipes.backend.rest.domain.IngredientRest;
import com.recipes.backend.rest.domain.RecipeRest;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(SpringExtension.class)
@Sql({"/data/recipe/insert-for-recipe-add.sql", "/data/recipe/insert-1-recipe.sql"})
class RecipeControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    void shouldAddRecipeGivenCorrectBody() throws JsonProcessingException {
        // given
        var recipeRest = new RecipeRest(1L, "TEST_NAME", "TEST_INSTRUCTION",
            Set.of(new IngredientRecipeRest(new IngredientRest(1000L, "Ingredient"),
                "10g")), Set.of("vegetarian"));
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(recipeRest), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(createURLWithPort("/recipes"),
            HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldNotAddRecipeGivenIncorrectBody() throws JsonProcessingException {
        // given
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        var recipeRest = new RecipeRest(1000L, "TEST_NAME", "TEST_INSTRUCTION",
            Set.of(new IngredientRecipeRest(new IngredientRest(1000L, "Ingredient"),
                "10g")), null);
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(recipeRest), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(createURLWithPort("/recipes"),
            HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
