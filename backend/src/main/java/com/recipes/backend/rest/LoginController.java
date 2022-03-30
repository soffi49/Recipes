package com.recipes.backend.rest;

import static com.recipes.backend.utils.LogWriter.logHeaders;

import com.recipes.backend.bizz.login.LoginService;
import com.recipes.backend.rest.domain.LoginRest;
import com.recipes.backend.rest.domain.TokenRest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<TokenRest> login(@RequestHeader HttpHeaders headers,
                                           @RequestBody @Valid LoginRest loginForm) {
        logHeaders(headers);

        final Optional<String> tokenOptional = loginService.loginToSystem(loginForm);

        return tokenOptional
                .map(s -> ResponseEntity.ok(new TokenRest(s)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
