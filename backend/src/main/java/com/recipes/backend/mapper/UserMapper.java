package com.recipes.backend.mapper;

import com.recipes.backend.bizz.user.domain.User;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.UserRest;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public static Optional<User> mapToUser(final UserRest userRest) {
        if (Objects.nonNull(userRest) && Objects.nonNull(userRest.getUsername())) {
            var user = new User();

            user.setUsername(userRest.getUsername());

            if (Objects.nonNull(userRest.getPassword())) {
                user.setPassword(userRest.getPassword());
            }

            if (Objects.nonNull(userRest.getSavedRecipes())) {
                user.setSavedRecipes(userRest.getSavedRecipes()
                    .stream()
                    .map(RecipeMapper::mapToRecipe)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet())
                );
            }

            return Optional.of(user);
        }
        return Optional.empty();
    }
}
