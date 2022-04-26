package com.recipes.backend.repo;

import com.recipes.backend.common.AbstractIntegrationTestConfig;
import com.recipes.backend.repo.domain.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends AbstractIntegrationTestConfig
{
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Get existing user by username")
    @Sql({"/data/truncate-db.sql", "/data/user/insert-1-user.sql"})
    @Order(1)
    void findByUsernameExistingUser()
    {
        final Optional<UserDTO> result = userRepository.findByUsername("username");

        assertThat(result).isPresent();
        assertThat(result.get().getPassword()).isEqualTo("password");
    }

    @Test
    @DisplayName("Get non existing user by username")
    @Order(2)
    void findByUsernameNonExistingUser()
    {
        final Optional<UserDTO> result = userRepository.findByUsername("username1");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Get existing user by token")
    @Order(3)
    void findByTokenExistingUser()
    {
        final Optional<UserDTO> result = userRepository.findByToken("security_token");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("username");
    }

    @Test
    @DisplayName("Get non existing user by token")
    @Order(4)
    void findByTokenNonExistingUser()
    {
        final Optional<UserDTO> result = userRepository.findByUsername("wrong_token");

        assertThat(result).isEmpty();
    }
}
