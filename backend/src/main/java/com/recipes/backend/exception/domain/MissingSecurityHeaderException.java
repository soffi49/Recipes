package com.recipes.backend.exception.domain;

public class MissingSecurityHeaderException extends RuntimeException
{
    private static final long serialVersionUID = 665130809821320523L;

    public MissingSecurityHeaderException()
    {
        super("The request in missing the security header");
    }
}
