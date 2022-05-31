package com.recipes.backend.rest;

import static com.recipes.backend.utils.LogWriter.logHeaders;

import com.recipes.backend.bizz.security.SecurityService;
import com.recipes.backend.bizz.user.UserService;
import com.recipes.backend.exception.domain.UserEmptyException;
import com.recipes.backend.mapper.UserMapper;
import com.recipes.backend.rest.domain.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PutMapping()
    public ResponseEntity<Object> updateRecipe(@RequestHeader HttpHeaders headers,
        @RequestBody UserRest userRest) {
        logHeaders(headers);
        securityService.isAuthenticated(headers);
        userService.updateUser(UserMapper.mapToUser(userRest).orElseThrow(UserEmptyException::new));
        return ResponseEntity.ok().build();
    }
}
