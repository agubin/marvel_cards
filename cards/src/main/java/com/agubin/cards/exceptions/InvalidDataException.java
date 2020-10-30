package com.agubin.cards.exceptions;

public class InvalidDataException extends CustomException {

    public InvalidDataException(String errorReason) {
        this.errorMessage = "Invalid data! " + errorReason;
    }
}
