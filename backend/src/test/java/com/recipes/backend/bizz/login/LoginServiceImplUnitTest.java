package com.recipes.backend.bizz.login;

import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    @ParameterizedTest
    @MethodSource("provideIncorrectLoginRests")
    void shouldInvalidateIncorrectLoginRequest(LoginRest loginRest, LoginRest realUser, Optional<UserDTO> userEntity) {
        // given
        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        // when
        Optional<String> token = loginService.loginToSystem(loginRest);

        // then
        assertThat(token)
                .as("for invalid login should return empty optional")
                .isEmpty();
    }

    static Stream<Arguments> provideIncorrectLoginRests() {
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