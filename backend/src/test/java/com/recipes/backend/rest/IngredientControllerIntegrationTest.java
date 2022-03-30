package com.recipes.backend.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.ErrorMode.CONTINUE_ON_ERROR;

import com.recipes.backend.rest.domain.IngredientRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.ErrorMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(SpringExtension.class)
@Sql("/data/insert-1-ingredient.sql")
public class IngredientControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    void shouldCorrectlyUpdateIngredientGivenExistingId() throws JsonProcessingException {
        // given
        var ingredient = new IngredientRest(1000L, "new_name");
        var expectedString = "{\"id\":1000,\"name\":\"new_name\"}";
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(ingredient), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(
            createURLWithPort("/ingredients/1000"),
            HttpMethod.PUT, entity, String.class);

        // then
        assertThat(response.getBody()).isEqualTo(expectedString);
    }

    @Test
    void shouldReturnErrorGivenWrongId() throws JsonProcessingException {
        // given
        var ingredient = new IngredientRest(100L, "new_name");
        var expectedString =
                "\"status\":500,\"error\":\"Internal Server Error\",\"path\":\"/ingredients/100\"";
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(ingredient), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(
            createURLWithPort("/ingredients/100"),
            HttpMethod.PUT, entity, String.class);

        // then
        assertThat(response.getBody()).contains(expectedString);
    }
}
