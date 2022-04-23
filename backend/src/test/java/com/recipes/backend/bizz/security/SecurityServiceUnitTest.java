package com.recipes.backend.bizz.security;

import com.recipes.backend.exception.domain.MissingSecurityHeaderException;
import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class SecurityServiceUnitTest
{

    private static final String HEADER_KEY = "security_header";
    private static final String HEADER_VALUE = "token";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityServiceImpl securityService;

    @ParameterizedTest
    @DisplayName("Is authenticated with correct header")
    @MethodSource("provideCorrectHttpHeaders")
    void isAuthenticatedForCorrectHeader(final HttpHeaders headers,
                                         final Optional<UserDTO> user,
                                         final boolean expectedResult)
    {
        lenient().when(userRepository.findByToken(anyString())).thenReturn(user);

        final boolean result = securityService.isAuthenticated(headers);

        assertThat(result).isEqualTo(expectedResult);

    }


    @ParameterizedTest
    @DisplayName("Is authenticated with incorrect header")
    @MethodSource("provideIncorrectHttpHeaders")
    void shouldAuthenticateForIncorrectHeader(final HttpHeaders headers,
                                              final Optional<UserDTO> user)
    {
        // given
        lenient().when(userRepository.findByToken(anyString())).thenReturn(user);

        // then
        assertThatThrownBy(() -> securityService.isAuthenticated(headers))
                .isExactlyInstanceOf(MissingSecurityHeaderException.class);
    }

    private static Stream<Arguments> provideIncorrectHttpHeaders()
    {
        return Stream.of(
                Arguments.of(null, Optional.of(new UserDTO())),
                Arguments.of(new HttpHeaders(), Optional.of(new UserDTO()))
                        );
    }

    private static Stream<Arguments> provideCorrectHttpHeaders()
    {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_KEY, HEADER_VALUE);

        return Stream.of(
                Arguments.of(headers, Optional.empty(), false),
                Arguments.of(headers, Optional.of(new UserDTO()), true)
                        );
    }
}