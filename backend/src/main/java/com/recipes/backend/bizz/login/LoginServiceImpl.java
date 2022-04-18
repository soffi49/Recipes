package com.recipes.backend.bizz.login;

import com.recipes.backend.exception.domain.IncorrectPasswordException;
import com.recipes.backend.exception.domain.UserNotFoundException;
import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService
{

    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(final UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public String loginToSystem(final LoginRest loginForm)
    {
        final UserDTO userDTO = userRepository.findByUsername(loginForm.getUsername()).orElseThrow(() -> new UserNotFoundException(loginForm.getUsername()));

        if (!userDTO.getPassword().equals(loginForm.getPassword()))
        {
            throw new IncorrectPasswordException(loginForm.getUsername());
        }

        return userDTO.getToken();
    }
}
