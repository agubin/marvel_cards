package com.agubin.cards.exceptions;

public class CustomException extends RuntimeException{

    protected String errorMessage;

    public CustomException() {
        this.errorMessage = "Empty error message";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
