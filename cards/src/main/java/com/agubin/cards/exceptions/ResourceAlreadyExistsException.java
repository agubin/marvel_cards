package com.agubin.cards.exceptions;

import com.agubin.cards.utils.LinkManager;

import java.net.URI;

public class ResourceAlreadyExistsException extends CustomException {

    public ResourceAlreadyExistsException(String resType, Long id) {
        String resURI = getResURI(resType, id);
        this.errorMessage = "Resource already exists" + resURI;
    }

    private String getResURI(String resType, Long id) {
        URI uri;
        switch (resType) {
            case ResourceTypes.CHR:
                uri = LinkManager.getCharacterURI(id);
                break;
            case ResourceTypes.COM:
                uri = LinkManager.getComicURI(id);
                break;
            case ResourceTypes.IMG:
                uri = LinkManager.getCharacterPortraitURI(id);
                break;
            default:
                uri = null;
        }
        return uri != null
                ? ", URI=" + uri + "."
                : ".";
    }
}
