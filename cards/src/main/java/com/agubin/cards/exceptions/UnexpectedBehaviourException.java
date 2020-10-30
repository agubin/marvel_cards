package com.agubin.cards.exceptions;

public class UnexpectedBehaviourException extends RuntimeException implements CustomException {

    private String errorMessage;

    public UnexpectedBehaviourException() {
        super();
        this.errorMessage = "Sorry, Internal Server Error. Try later.";
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
