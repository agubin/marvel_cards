package com.agubin.cards.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@PropertySource("classpath:application.properties")
public class LinkManager {

    @Autowired
    private Environment env;

    @Autowired
    private void setSchema() {
        LinkManager.schema = env.getProperty("schema");
    }

    @Autowired
    private  void setHost() {
        LinkManager.host = env.getProperty("host");
    }

    @Autowired
    private  void setPort() {
        LinkManager.port = env.getProperty("server.port");
    }


    private static String schema;
    private static String host;
    private static String port;

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
