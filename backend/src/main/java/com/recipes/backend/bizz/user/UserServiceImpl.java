package com.recipes.backend.bizz.user;

import com.recipes.backend.bizz.user.domain.User;
import com.recipes.backend.exception.domain.DatabaseSaveException;
import com.recipes.backend.exception.domain.UserNotFoundException;
import com.recipes.backend.mapper.RecipeMapper;
import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUser(User user) {
        final UserDTO userToBeUpdated = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new UserNotFoundException(user.getUsername()));

        if(Objects.nonNull(user.getPassword())) {
            userToBeUpdated.setPassword(user.getPassword());
        }

        if(Objects.nonNull(user.getSavedRecipes()) && !user.getSavedRecipes().isEmpty()) {
            userToBeUpdated.getRecipeSet().addAll(
                user.getSavedRecipes()
                    .stream()
                    .map(RecipeMapper::mapToRecipeDTO)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList()));
        }

        try {
            userRepository.save(userToBeUpdated);
        } catch (final DataAccessException e) {
            throw  new DatabaseSaveException("couldn't update given user");
        }
    }
}
