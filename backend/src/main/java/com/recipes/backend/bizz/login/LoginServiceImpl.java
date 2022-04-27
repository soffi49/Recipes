package com.recipes.backend.bizz.login;

import com.recipes.backend.bizz.login.domain.UserToken;
import com.recipes.backend.exception.domain.IncorrectPasswordException;
import com.recipes.backend.exception.domain.UserAlreadyExistsException;
import com.recipes.backend.exception.domain.UserEmptyException;
import com.recipes.backend.exception.domain.UserNotFoundException;
import com.recipes.backend.mapper.UserMapper;
import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import com.recipes.backend.rest.domain.TokenRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserToken loginToSystem(final LoginRest loginForm) {
        final UserDTO userDTO = userRepository.findByUsername(loginForm.getUsername()).orElseThrow(() -> new UserNotFoundException(loginForm.getUsername()));

        if (!userDTO.getPassword().equals(loginForm.getPassword())) {
            throw new IncorrectPasswordException(loginForm.getUsername());
        }

        return new UserToken(userDTO.getToken(), userDTO.getIsAdmin() == 1);
    }

    @Override
    public UserToken registerUser(LoginRest loginForm) {

        userRepository.findByUsername(loginForm.getUsername()).ifPresent(s -> {
            throw new UserAlreadyExistsException(loginForm.getUsername());
        });

        final UserDTO userDTO = UserMapper.mapToUserDTO(loginForm.getUsername(), loginForm.getPassword()).orElseThrow(UserEmptyException::new);
        userRepository.save(userDTO);

        return new UserToken(userDTO.getToken(), false);
    }
}
