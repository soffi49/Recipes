package com.recipes.backend.mapper;

import com.recipes.backend.bizz.login.domain.UserToken;
import com.recipes.backend.bizz.login.mapper.TokenMapper;
import com.recipes.backend.rest.domain.TokenRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TokenMapperTest {

    private UserToken mockUserToken;

    @BeforeEach
    public void setUp() {
        mockUserToken = new UserToken("token", true);
    }

    @Test
    @DisplayName("Map to tokenRest from userToken correct data")
    void mapToTokenRestFromUserTokenAllData() {
        final Optional<TokenRest> retrievedTokenRest = TokenMapper.mapToTokenRest(mockUserToken);

        Assertions.assertTrue(retrievedTokenRest.isPresent());
        Assertions.assertEquals("token", retrievedTokenRest.get().getToken());
    }

    @Test
    @DisplayName("Map to tokenRest from userToken null data")
    void mapToTokenRestFromUserTokenNull() {
        final Optional<TokenRest> retrievedTokenRest = TokenMapper.mapToTokenRest(null);

        Assertions.assertFalse(retrievedTokenRest.isPresent());
    }
}
