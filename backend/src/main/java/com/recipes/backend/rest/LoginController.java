package com.recipes.backend.rest;

import com.recipes.backend.bizz.login.LoginService;
import com.recipes.backend.rest.domain.LoginRest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.recipes.backend.utils.LogWriter.logHeaders;


@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestHeader HttpHeaders headers,
                                        @RequestBody LoginRest loginForm) {
        logHeaders(headers);

        final Optional<String> tokenOptional = loginService.loginToSystem(loginForm);
        return tokenOptional.map(s -> ResponseEntity.ok(JSONObject.quote(s)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
