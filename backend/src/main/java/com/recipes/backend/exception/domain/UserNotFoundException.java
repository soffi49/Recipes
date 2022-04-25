package com.recipes.backend.exception.domain;

public class UserNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 8636821766373160170L;

    public UserNotFoundException(final String userName)
    {
        super(String.format("User %s does not exist", userName));
    }
}
