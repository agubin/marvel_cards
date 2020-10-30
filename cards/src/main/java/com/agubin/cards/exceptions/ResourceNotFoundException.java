package com.agubin.cards.exceptions;


import com.agubin.cards.utils.LinkManager;

import java.util.List;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String resType, Long id) {
        this.errorMessage = getMessage(resType, id);
    }

    public ResourceNotFoundException(String resType, List<Long> id) {
        this.errorMessage = getMessage(resType, id);
    }

    public ResourceNotFoundException(String resType, Long characterId, Long comicId) {
        this.errorMessage = getMessage(resType, characterId, comicId);
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

    private String getMessage(String resType, List<Long> id) {
        switch (resType) {
            case ResourceTypes.CHR:
            case ResourceTypes.COM:
                return resType + "s with id=" + id + " not found!";
            default:
                return "The Resource not found!";
        }
    }

    private String getMessage(String resType, Long characterId, Long comicId) {
        switch (resType) {
            case ResourceTypes.CHR_COM:
                return String.format("Relation between %s(uri=%s) and %s(uri=%s) not found!",
                        ResourceTypes.CHR, LinkManager.getCharacterURI(characterId),
                        ResourceTypes.COM, LinkManager.getComicURI(comicId));
            default:
                return "The Resource not found!";
        }
    }
}
