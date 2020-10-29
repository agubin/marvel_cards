package com.agubin.cards.utils;

import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalComicResRepr {

    private Comics comic;
    private int numberOfRelatedCharacters;
    private List<CharacterShortView> relatedCharacters;
//    private CharactersCollectionResRepr comicCharacters;
    private Map<String, URI> relatedURI;

    public PersonalComicResRepr(Comics comic, List<Character> characterList) {
        this.comic = comic;
        CharactersCollectionResRepr comicCharacters = new CharactersCollectionResRepr(characterList, comic.getId());
        this.numberOfRelatedCharacters = comicCharacters.getNumberOfCharacters();
        this.relatedCharacters = comicCharacters.getCharacters();
        this.relatedURI = new HashMap<>();
        relatedURI.put("allComicsURI", LinkManager.getAllComicsURI());
        relatedURI.put("TheComicURI", LinkManager.getComicURI(comic.getId()));
        relatedURI.put("ComicCharactersURI", LinkManager.getComicCharactersURI(comic.getId()));

    }

    public Comics getComic() {
        return comic;
    }

//    public CharactersCollectionResRepr getComicCharacters() {
//        return comicCharacters;
//    }

    public int getNumberOfRelatedCharacters() {
        return numberOfRelatedCharacters;
    }

    public List<CharacterShortView> getRelatedCharacters() {
        return relatedCharacters;
    }

    public Map<String, URI> getRelatedURI() {
        return relatedURI;
    }
}
