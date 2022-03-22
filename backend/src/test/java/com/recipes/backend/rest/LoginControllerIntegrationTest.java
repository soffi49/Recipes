package com.recipes.backend.rest;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.rest.domain.LoginRest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@Sql({"/data/drop-db-if-exists.sql","/data/create-user-db.sql", "/data/insert-1-user.sql"})
class LoginControllerIntegrationTest extends AbstractIntegrationTestConfig {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final TestRestTemplate REST_TEMPLATE = new TestRestTemplate();
    private static final HttpHeaders HEADERS = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Test
    void shouldCorrectlyLoginGivenCorrectCredentials() throws JsonProcessingException {
        // given
        HEADERS.add("Content-type", "application/json");
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
    void shuouldNotLoginGivenIncorrectCredentials() throws JsonProcessingException {
        // given
        HEADERS.add("Content-type", "application/json");
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

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
