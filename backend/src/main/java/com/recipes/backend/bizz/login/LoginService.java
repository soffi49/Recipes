package com.recipes.backend.bizz.login;

import com.recipes.backend.bizz.login.domain.UserToken;
import com.recipes.backend.rest.domain.LoginRest;
import com.recipes.backend.rest.domain.TokenRest;

public interface LoginService {

    UserToken loginToSystem(LoginRest loginForm);

    UserToken registerUser(LoginRest loginForm);
}
