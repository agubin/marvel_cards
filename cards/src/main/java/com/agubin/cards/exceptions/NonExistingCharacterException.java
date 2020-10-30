package com.agubin.cards.exceptions;

import java.util.List;

public class NonExistingCharacterException extends CustomException {

    public NonExistingCharacterException(List<Long> invalidId) {
        this.errorMessage = "Character(s) with id : " + invalidId + " does(do) not exist!";
    }
}
