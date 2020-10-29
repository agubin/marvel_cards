package com.agubin.cards.exceptions;

import java.util.List;

public class ResourceNotFoundException extends Exception {

    private String errorMessage;

    public ResourceNotFoundException(String errMes) {
        this.errorMessage = errMes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
