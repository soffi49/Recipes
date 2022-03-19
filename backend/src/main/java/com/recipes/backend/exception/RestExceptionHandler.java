package com.recipes.backend.exception;

import com.recipes.backend.exception.domain.DatabaseSaveException;
import com.recipes.backend.exception.domain.ExceptionTypeEnum;
import com.recipes.backend.exception.domain.IngredientDuplicateException;
import com.recipes.backend.exception.domain.IngredientEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IngredientDuplicateException.class)
    protected ResponseEntity<Object> handleForbiddenException(final IngredientDuplicateException ex,
                                                               final WebRequest webRequest) {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.DATABASE_DUPLICATE;
        return handleExceptionInternal(ex, exceptionType.getMessage(), new HttpHeaders(), exceptionType.getStatus(), webRequest);
    }

    @ExceptionHandler(IngredientEmptyException.class)
    protected ResponseEntity<Object> handleForbiddenException(final IngredientEmptyException ex,
                                                              final WebRequest webRequest) {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.MAPPER_NULL;
        return handleExceptionInternal(ex, exceptionType.getMessage(), new HttpHeaders(), exceptionType.getStatus(), webRequest);
    }

    @ExceptionHandler(DatabaseSaveException.class)
    protected ResponseEntity<Object> handleInternalException(final IngredientDuplicateException ex,
                                                               final WebRequest webRequest) {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.DATABASE_INTERNAL;
        return handleExceptionInternal(ex, exceptionType.getMessage(), new HttpHeaders(), exceptionType.getStatus(), webRequest);
    }

}
