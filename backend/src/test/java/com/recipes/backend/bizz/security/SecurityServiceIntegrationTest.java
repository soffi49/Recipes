package com.recipes.backend.bizz.security;

import com.recipes.backend.bizz.recipe.RecipeService;
import com.recipes.backend.bizz.recipe.domain.Recipe;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.RecipeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityServiceIntegrationTest  extends AbstractIntegrationTestConfig
{
    private static final String HEADER_KEY = "security_header";
    private static final String HEADER_VALUE = "security_token";
    private static HttpHeaders headers;

    @Autowired
    private SecurityService securityService;

    @BeforeAll
    static void setUp()
    {
        headers = new HttpHeaders();
        headers.add(HEADER_KEY, HEADER_VALUE);
    }

    @Test
    @DisplayName("Is authenticated for correct header and correct token")
    @Sql({"/data/truncate-db.sql","/data/user/insert-1-user.sql"})
    @Order(1)
    void isAuthenticatedCorrect()
    {
        final boolean result = securityService.isAuthenticated(headers);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Is authenticated for correct header and incorrect token")
    @Sql({"/data/truncate-db.sql"})
    @Order(2)
    void isAuthenticatedIncorrectUser()
    {
        final boolean result = securityService.isAuthenticated(headers);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Is authenticated for correct header and empty db")
    @Order(3)
    void isAuthenticatedEmptyDatabase()
    {
        final boolean result = securityService.isAuthenticated(headers);

        assertThat(result).isFalse();
    }

}
