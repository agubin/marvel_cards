package com.agubin.cards.exceptions;

import java.util.List;

public class ResourceNotFoundException extends RuntimeException implements CustomException {

    private String errorMessage;

    public ResourceNotFoundException(String errMes) {
        super();
        this.errorMessage = errMes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
