package com.recipes.backend.rest;

import com.recipes.backend.rest.domain.LoginRest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@ExtendWith(SpringExtension.class)
@Sql("/data/user/insert-1-user.sql")
class LoginControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Test
    void shouldCorrectlyLoginGivenCorrectCredentials() throws JsonProcessingException {
        // given
        var user = new LoginRest("username", "password");
        var expectedString = "{\"token\":\"security_token\"}";
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(user), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(
                createURLWithPort("/login"),
                HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getBody()).isEqualTo(expectedString);
    }

    @Test
    void shouldNotLoginGivenIncorrectCredentials() throws JsonProcessingException {
        // given
        var user = new LoginRest("username", "wrongPassword");
        var expectedString = "{\"token\":\"security_token\"}";
        var entity = new HttpEntity<>(MAPPER.writeValueAsString(user), HEADERS);

        // when
        ResponseEntity<String> response = REST_TEMPLATE.exchange(
                createURLWithPort("/login"),
                HttpMethod.POST, entity, String.class);

        // then
        assertThat(response).matches(r -> r.getStatusCode() == HttpStatus.FORBIDDEN);
    }
}
