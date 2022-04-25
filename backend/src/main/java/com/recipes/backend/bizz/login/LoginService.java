package com.recipes.backend.bizz.login;

import com.recipes.backend.rest.domain.LoginRest;

import java.util.Optional;

public interface LoginService {

    String loginToSystem(LoginRest loginForm);
}
