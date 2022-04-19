package com.recipes.backend.rest;

import com.recipes.backend.rest.domain.IngredientRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientControllerIntegrationTest extends AbstractControllerIntegrationTest
{

    @Test
    @DisplayName("Update existing ingredient")
    @Sql("/data/ingredient/insert-1-ingredient.sql")
    @Order(1)
    void shouldCorrectlyUpdateIngredientGivenExistingId() throws JsonProcessingException
    {
        // given
        var ingredient = new IngredientRest(1000L, "new_name");
        var expectedString = "{\"id\":1000,\"name\":\"new_name\"}";
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(ingredient), HEADERS);

        // when
        final ResponseEntity<String> response = REST_TEMPLATE.exchange(
                createURLWithPort("/ingredients/1000"),
                HttpMethod.PUT,
                entity,
                String.class);

        // then
        assertThat(response.getBody()).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Update non existing ingredient")
    @Order(2)
    void shouldReturnErrorGivenWrongId() throws JsonProcessingException
    {
        // given
        var ingredient = new IngredientRest(100L, "new_name");
        var expectedString = "The object was not found";
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(ingredient), HEADERS);

        // when
        final ResponseEntity<String> response = REST_TEMPLATE.exchange(
                createURLWithPort("/ingredients/100"),
                HttpMethod.PUT,
                entity,
                String.class);

        // then
        assertThat(response.getBody()).contains(expectedString);
    }
}
