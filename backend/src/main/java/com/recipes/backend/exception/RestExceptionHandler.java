package com.recipes.backend.exception;

import com.recipes.backend.exception.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    //Ingredient

    @ExceptionHandler(IngredientDuplicateException.class)
    protected ResponseEntity<Object> handleForbiddenException(final IngredientDuplicateException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.DATABASE_DUPLICATE;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(IngredientEmptyException.class)
    protected ResponseEntity<Object> handleForbiddenException(final IngredientEmptyException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.MAPPER_NULL;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(IngredientNotFound.class)
    protected ResponseEntity<Object> handleNotFoundException(final IngredientNotFound ex,
                                                             final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.NOT_FOUND;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    //Recipe

    @ExceptionHandler(RecipeDuplicateException.class)
    protected ResponseEntity<Object> handleForbiddenException(final RecipeDuplicateException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.DATABASE_DUPLICATE;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(RecipeEmptyException.class)
    protected ResponseEntity<Object> handleForbiddenException(final RecipeEmptyException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.MAPPER_NULL;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(RecipeNotFound.class)
    protected ResponseEntity<Object> handleNotFoundException(final RecipeNotFound ex,
                                                             final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.NOT_FOUND;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(MissingQuantityException.class)
    protected ResponseEntity<Object> handleForbiddenException(final MissingQuantityException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.MAPPER_NULL;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    //Tag

    @ExceptionHandler(TagNotFound.class)
    protected ResponseEntity<Object> handleNotFoundException(final TagNotFound ex,
                                                             final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.NOT_FOUND;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    //Login and security

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleForbiddenException(final UserNotFoundException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.INCORRECT_CREDENTIALS;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    protected ResponseEntity<Object> handleForbiddenException(final IncorrectPasswordException ex,
                                                              final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.INCORRECT_CREDENTIALS;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    //Common

    @ExceptionHandler(DatabaseSaveException.class)
    protected ResponseEntity<Object> handleInternalException(final DatabaseSaveException ex,
                                                             final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.DATABASE_INTERNAL;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    @ExceptionHandler(DatabaseFindException.class)
    protected ResponseEntity<Object> handleInternalException(final DatabaseFindException ex,
                                                             final WebRequest webRequest)
    {
        final ExceptionTypeEnum exceptionType = ExceptionTypeEnum.DATABASE_INTERNAL;
        return handleCustomException(exceptionType, ex, webRequest);
    }

    private ResponseEntity<Object> handleCustomException(final ExceptionTypeEnum exceptionTypeEnum,
                                                         final Exception exception,
                                                         final WebRequest webRequest)
    {

        log.error(String.format("Exception: %s", exceptionTypeEnum.getMessage()));
        exception.printStackTrace();
        return handleExceptionInternal(exception, exceptionTypeEnum.getMessage(), new HttpHeaders(), exceptionTypeEnum.getStatus(), webRequest);
    }
}
