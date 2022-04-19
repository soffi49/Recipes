package com.recipes.backend.rest;

import com.recipes.backend.rest.domain.LoginRest;
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

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
class LoginControllerIntegrationTest extends AbstractControllerIntegrationTest
{

    @Test
    @DisplayName("Log in with correct credentials")
    @Sql({"/data/truncate-db.sql", "/data/user/insert-1-user.sql"})
    @Order(1)
    void shouldCorrectlyLoginGivenCorrectCredentials() throws JsonProcessingException
    {
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
    @DisplayName("Log in with incorrect credentials")
    @Order(2)
    void shouldNotLoginGivenIncorrectCredentials() throws JsonProcessingException
    {
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
