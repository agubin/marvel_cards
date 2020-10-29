package com.agubin.cards.utils;

import java.net.URI;

public class LinkManager {

    private static String schema = "https";
    private static String host = "localhost";
    private static String port = "8080";

    private static String charactersResource = "characters";
    private static String comicsResource = "comics";
    private static String portraitResource = "portrait";


    private static String getMainEndpoint() {
        return schema + "://" + host + ":" + port + "/";
    }

    static public URI getAllCharactersURI() {
        return URI.create(getMainEndpoint() + charactersResource);
    }

    static public URI getCharacterURI(Long characterId) {
        return URI.create(getAllCharactersURI() + "/" +characterId);
    }

    static public URI getCharacterComicsURI(Long characterId) {
        return URI.create(getCharacterURI(characterId) + "/" + comicsResource);
    }

    static public URI getAllComicsURI() {
        return URI.create(getMainEndpoint() + comicsResource);
    }

    static public URI getComicURI(Long comicId) {
        return URI.create(getAllComicsURI() + "/" + comicId);
    }

    static public URI getComicCharactersURI(Long comicId) {
        return URI.create(getComicURI(comicId) + "/" + charactersResource);
    }

    static public URI getCharacterPortraitURI(Long characterId) {
        return URI.create(getCharacterURI(characterId) + "/" + portraitResource);
    }
}
