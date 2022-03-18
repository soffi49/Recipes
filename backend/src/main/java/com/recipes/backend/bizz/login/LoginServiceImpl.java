package com.recipes.backend.bizz.login;

import com.recipes.backend.repo.UserRepository;
import com.recipes.backend.repo.domain.UserDTO;
import com.recipes.backend.rest.domain.LoginRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(final UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<String> loginToSystem(final LoginRest loginForm)
    {
        var userOptional = userRepository.findByUsername(loginForm.getUsername());

        if(userOptional.isEmpty())
        {
            log.warn("User {} doesn't exists", loginForm.getUsername());
            return Optional.empty();
        }

        if(!userOptional.get().getPassword().equals(loginForm.getPassword())) {
            log.warn("Wrong password for user {}", loginForm.getUsername());
            return Optional.empty();
        }

        return userOptional.map(UserDTO::getSecurityToken);
    }
}
