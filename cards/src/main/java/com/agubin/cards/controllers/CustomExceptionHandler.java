package com.agubin.cards.controllers;

import com.agubin.cards.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class,
                               NonExistingCharacterException.class,
                               InvalidEntityException.class})
    public ResponseEntity<Object> handleCustomException(CustomException ex) {

        HttpStatus status;

        if (ex.getClass().isAssignableFrom(ResourceNotFoundException.class)) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getClass().isAssignableFrom(NonExistingCharacterException.class)) {
            status = HttpStatus.CONFLICT;
        } else if (ex.getClass().isAssignableFrom(InvalidEntityException.class)) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ApiError apiError = new ApiError(status, ex.getErrorMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
