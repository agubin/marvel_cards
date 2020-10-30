package com.agubin.cards.exceptions;

public class InvalidEntityException extends RuntimeException implements CustomException {

    private String errorMessage;

    public InvalidEntityException(String errMes) {
        super();
        this.errorMessage = errMes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
