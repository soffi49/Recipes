package com.recipes.backend.bizz.login;

import com.recipes.backend.rest.domain.LoginRest;

public interface LoginService {

    String loginToSystem(LoginRest loginForm);

    String registerUser(LoginRest loginForm);
}
