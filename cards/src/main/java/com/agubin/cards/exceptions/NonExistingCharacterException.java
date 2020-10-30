package com.agubin.cards.exceptions;

import java.util.List;

public class NonExistingCharacterException  extends RuntimeException implements CustomException{

    private String errorMessage;

    public NonExistingCharacterException(List<Long> invalidId) {
        super();
        this.errorMessage = "Character(s) with id : " + invalidId + " does(do) not exist!";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
