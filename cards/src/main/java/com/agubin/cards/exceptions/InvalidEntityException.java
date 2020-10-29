package com.agubin.cards.exceptions;

public class InvalidEntityException extends Exception{

    private String errorMessage;

    public InvalidEntityException(String errMes) {
        this.errorMessage = errMes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
