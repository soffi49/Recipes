package com.recipes.backend.bizz.login;

import com.recipes.backend.bizz.login.domain.UserToken;
import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.exception.domain.IncorrectPasswordException;
import com.recipes.backend.exception.domain.UserAlreadyExistsException;
import com.recipes.backend.exception.domain.UserNotFoundException;
import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import com.recipes.backend.rest.domain.TokenRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoginServiceIntegrationTest extends AbstractIntegrationTestConfig {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Log in to system with correct credentials")
    @Sql({"/data/truncate-db.sql", "/data/user/insert-1-user.sql"})
    @Order(1)
    void logInToSystemCorrectCredentials() {
        final LoginRest loginRest = new LoginRest("username", "password");

        final UserToken token = loginService.loginToSystem(loginRest);

        assertThat(token.getUserToken()).isEqualTo("security_token");
    }

    @Test
    @DisplayName("Log in to system with incorrect user")
    @Order(2)
    void logInToSystemInCorrectUser() {
        final LoginRest loginRest = new LoginRest("username1", "password");

        assertThatThrownBy(() -> loginService.loginToSystem(loginRest))
                .isInstanceOfAny(UserNotFoundException.class)
                .hasMessage("User username1 does not exist");
    }

    @Test
    @DisplayName("Log in to system with incorrect password")
    @Order(3)
    void logInToSystemInCorrectPassword() {
        final LoginRest loginRest = new LoginRest("username", "password1");

        assertThatThrownBy(() -> loginService.loginToSystem(loginRest))
                .isInstanceOfAny(IncorrectPasswordException.class)
                .hasMessage("Wrong password provided for user username");
    }

    @Test
    @DisplayName("Register not existing user")
    @Sql({"/data/truncate-db.sql"})
    @Order(4)
    void registerNotExistingUser() {
        final LoginRest loginRest = new LoginRest("username2", "password2");

        final UserToken token = loginService.registerUser(loginRest);

        final Optional<UserDTO> retrievedUser = userRepository.findByUsername("username2");

        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getToken()).isEqualTo(token.getUserToken());
        assertThat(retrievedUser.get().getPassword()).isEqualTo("password2");
    }

    @Test
    @DisplayName("Register already existing user")
    @Sql({"/data/truncate-db.sql", "/data/user/insert-1-user.sql"})
    @Order(5)
    void registerAlreadyExistingUser() {
        final LoginRest loginRest = new LoginRest("username", "password");

        assertThatThrownBy(() -> loginService.registerUser(loginRest))
                .isInstanceOfAny(UserAlreadyExistsException.class)
                .hasMessage("User username already exists");
    }
}
