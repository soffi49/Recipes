package com.recipes.backend.rest;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@Sql({"/data/truncate-db.sql", "/data/user/insert-test-user.sql"})
abstract class AbstractControllerIntegrationTest extends AbstractIntegrationTestConfig
{

    protected static final ObjectMapper MAPPER = new ObjectMapper();
    protected static final TestRestTemplate REST_TEMPLATE = new TestRestTemplate();
    protected static final HttpHeaders HEADERS = new HttpHeaders();

    @LocalServerPort
    protected int port;

    @BeforeEach
    void init()
    {
        HEADERS.add("Content-type", "application/json");
        HEADERS.add("security_header", "test-token");
    }

    protected String createURLWithPort(String uri)
    {
        return "http://localhost:" + port + uri;
    }
}
