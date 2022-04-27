package com.recipes.backend.bizz.login.mapper;

import com.recipes.backend.bizz.login.domain.UserToken;
import com.recipes.backend.rest.domain.TokenRest;

import java.util.Objects;
import java.util.Optional;

public class TokenMapper {

    public static Optional<TokenRest> mapToTokenRest(final UserToken userToken) {

        if(Objects.nonNull(userToken)){
            return Optional.of(new TokenRest(userToken.getUserToken(), userToken.getIsAdmin() ? 1 : 0));
        }

        return Optional.empty();
    }
}
