package com.agubin.cards.exceptions;


public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String resType, Long id) {
        this.errorMessage = getMessage(resType, id);
    }

    private String getMessage(String resType, Long id) {
        switch (resType) {
            case ResourceTypes.CHR:
            case ResourceTypes.COM:
                return resType + " with id=" + id + " not found!";
            case ResourceTypes.IMG:
                return resType + "for character with id=" + id + "not found!";
            default:
                return "The Resource not found!";
        }
    }
}
