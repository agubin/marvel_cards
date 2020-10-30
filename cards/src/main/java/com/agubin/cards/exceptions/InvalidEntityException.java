package com.agubin.cards.exceptions;

public class InvalidEntityException extends CustomException {

    public InvalidEntityException(String errMes) {
        this.errorMessage = errMes;
    }
}
