package com.recipes.backend.bizz.security;

import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
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
    @MethodSource("provideHttpHeaders")
    void shouldCorrectlyAuthenticate(HttpHeaders headers, Optional<UserDTO> user, boolean expectedResult)
    {
        // given
        lenient().when(userRepository.findByToken(anyString())).thenReturn(user);

        // when
        var result = securityService.isAuthenticated(headers);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> provideHttpHeaders()
    {
        var headers = new HttpHeaders();
        headers.add(HEADER_KEY, HEADER_VALUE);

        return Stream.of(
                Arguments.of(headers, Optional.of(new UserDTO()), true),
                Arguments.of(headers, Optional.empty(), false),
                Arguments.of(null, Optional.of(new UserDTO()), false),
                Arguments.of(new HttpHeaders(), Optional.of(new UserDTO()), false)
                        );
    }
}