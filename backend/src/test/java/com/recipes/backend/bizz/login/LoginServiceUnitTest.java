package com.recipes.backend.bizz.login;

import com.recipes.backend.exception.domain.IncorrectPasswordException;
import com.recipes.backend.exception.domain.UserNotFoundException;
import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceUnitTest
{

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    @DisplayName("Log in with correct credentials")
    void loginToSystemCorrectCredentials()
    {
        final UserDTO userEntity = new UserDTO();
        userEntity.setUsername("username");
        userEntity.setPassword("password");
        userEntity.setToken("token");

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(userEntity));

        final String token = loginService.loginToSystem(new LoginRest("username", "password"));

        assertThat(token).isEqualTo("token");
    }

    @ParameterizedTest
    @DisplayName("Log in with incorrect credentials")
    @MethodSource("provideIncorrectLoginRests")
    void shouldInvalidateIncorrectLoginRequest(LoginRest loginRest, LoginRest realUser, Optional<UserDTO> userEntity)
    {
        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        assertThatThrownBy(() -> loginService.loginToSystem(loginRest))
                .isInstanceOfAny(UserNotFoundException.class, IncorrectPasswordException.class);
    }

    private static Stream<Arguments> provideIncorrectLoginRests()
    {
        var userEntity = new UserDTO();
        userEntity.setPassword("pass2");

        return Stream.of(
                Arguments.of(
                        new LoginRest("user1", "pass1"),
                        new LoginRest("user2", "pass2"),
                        Optional.empty()),
                Arguments.of(
                        new LoginRest("user2", "pass1"),
                        new LoginRest("user2", "pass2"),
                        Optional.of(userEntity))
                        );
    }
}