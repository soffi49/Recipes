package com.recipes.backend.exception.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionTypeEnum {
    MAPPER_NULL("The object passed to mapper is null", HttpStatus.FORBIDDEN),
    NOT_FOUND("The object was not found", HttpStatus.NOT_FOUND),
    MISSING_HEADER("The authorization header is missing", HttpStatus.UNAUTHORIZED),
    INCORRECT_CREDENTIALS("Provided login credentials are incorrect", HttpStatus.FORBIDDEN),
    DATABASE_DUPLICATE("The object already exists in the database", HttpStatus.FORBIDDEN),
    DATABASE_INTERNAL("There was internal database error", HttpStatus.INTERNAL_SERVER_ERROR);

    private String message;
    private HttpStatus status;

    ExceptionTypeEnum(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
