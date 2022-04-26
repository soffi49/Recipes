package com.recipes.backend.rest;

import com.recipes.backend.bizz.login.LoginService;
import com.recipes.backend.rest.domain.LoginRest;
import com.recipes.backend.rest.domain.TokenRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.recipes.backend.utils.LogWriter.logHeaders;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final LoginService loginService;

    @Autowired
    public RegisterController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<TokenRest> register(@RequestHeader HttpHeaders headers,
                                              @RequestBody @Valid LoginRest loginForm) {
        logHeaders(headers);

        final String retrievedToken = loginService.registerUser(loginForm);

        return ResponseEntity.ok(new TokenRest(retrievedToken));
    }
}


