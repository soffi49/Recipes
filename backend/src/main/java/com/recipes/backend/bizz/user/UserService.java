package com.recipes.backend.bizz.user;

import com.recipes.backend.bizz.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void updateUser(User user);
}
