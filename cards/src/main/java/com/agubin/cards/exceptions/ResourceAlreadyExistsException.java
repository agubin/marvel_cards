package com.agubin.cards.exceptions;

import com.agubin.cards.utils.LinkManager;

import java.net.URI;

public class ResourceAlreadyExistsException extends CustomException {

    public ResourceAlreadyExistsException(String resType, Long id) {
        String resURI = getResURI(resType, id);
        this.errorMessage = "Resource already exists" + resURI;
    }

    public ResourceAlreadyExistsException(String resType, Long characterId, Long comicId) {
        String characterURI = getResURI(ResourceTypes.CHR, characterId);
        String comicURI = getResURI(ResourceTypes.COM, comicId);
        this.errorMessage = String.format("Relation between %s(uri=%s) and %s(uri=%s) already exists!",
                                           ResourceTypes.CHR, characterURI, ResourceTypes.COM, comicURI);
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
