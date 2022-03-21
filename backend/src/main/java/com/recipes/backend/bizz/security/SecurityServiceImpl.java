package com.recipes.backend.bizz.security;

import com.recipes.backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityServiceImpl implements SecurityService {

    private static final String SECURITY_HEADER = "security_header";

    private final UserRepository userRepository;

    @Autowired
    public SecurityServiceImpl(final UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAuthenticated(HttpHeaders headers)
    {
        if (Objects.isNull(headers) || !headers.containsKey(SECURITY_HEADER))
        {
            return false;
        }

        return userRepository.findByToken(headers.getFirst(SECURITY_HEADER)).isPresent();
    }
}
