package com.recipes.backend.exception.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionTypeEnum {
    MAPPER_NULL("The object passed to mapper is null", HttpStatus.FORBIDDEN),
    DATABASE_DUPLICATE("The object already exists in the database", HttpStatus.FORBIDDEN),
    DATABASE_INTERNAL("There was internal database error", HttpStatus.INTERNAL_SERVER_ERROR);

    private String message;
    private HttpStatus status;

    ExceptionTypeEnum(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
