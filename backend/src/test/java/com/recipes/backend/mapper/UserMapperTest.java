package com.recipes.backend.mapper;

import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class UserMapperTest {

    private LoginRest mockLoginForm;

    @BeforeEach
    public void setUp() {
        mockLoginForm = new LoginRest("username", "password");
    }

    @Test
    @DisplayName("Map to userDTO from LoginRest all data")
    void mapToUserDTOFromLoginRestAllData() {
        final Optional<UserDTO> retrievedUserDTO = UserMapper.mapToUserDTO(mockLoginForm.getUsername(), mockLoginForm.getPassword());

        Assertions.assertTrue(retrievedUserDTO.isPresent());
        Assertions.assertEquals("username", retrievedUserDTO.get().getUsername());
    }

    @Test
    @DisplayName("Map to userDTO from LoginRest null")
    void mapToUserDTOFromLoginRestNull() {
        final Optional<UserDTO> retrievedUserDTO = UserMapper.mapToUserDTO(null, null);

        Assertions.assertFalse(retrievedUserDTO.isPresent());
    }
}
