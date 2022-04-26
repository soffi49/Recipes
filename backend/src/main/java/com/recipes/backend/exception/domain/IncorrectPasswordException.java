package com.recipes.backend.exception.domain;

public class IncorrectPasswordException extends RuntimeException
{
    private static final long serialVersionUID = 5014814472115684970L;

    public IncorrectPasswordException(final String userName)
    {
        super(String.format("Wrong password provided for user %s", userName));
    }
}
