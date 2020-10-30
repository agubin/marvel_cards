package com.agubin.cards.exceptions;

public class UnexpectedBehaviourException extends CustomException {

    public UnexpectedBehaviourException() {
        super();
        this.errorMessage = "Sorry, Internal Server Error. Try later.";
    }
}
