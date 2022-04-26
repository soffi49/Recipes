package com.recipes.backend.mapper;

import com.recipes.backend.repo.domain.UserDTO;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserMapper {

    public static Optional<UserDTO> mapToUserDTO(final String userName,
                                                 final String password) {
        if (Objects.nonNull(userName) && Objects.nonNull(password)) {
            final UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userName);
            userDTO.setPassword(password);
            userDTO.setIsAdmin(0);
            userDTO.setToken(UUID.randomUUID().toString());

            return Optional.of(userDTO);
        }
        return Optional.empty();
    }
}
