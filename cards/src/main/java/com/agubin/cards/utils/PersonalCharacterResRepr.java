package com.agubin.cards.utils;

import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalCharacterResRepr {

    private Character character;
    private int numberOfRelatedComics;
    private List<ComicShortView> relatedComics;
//    private ComicsCollectionResRepr characterComics;
    private Map<String, URI> relatedURI;

    public PersonalCharacterResRepr(Character character, List<Comics> comics) {
        this.character = character;
        ComicsCollectionResRepr characterComics = new ComicsCollectionResRepr(comics);
        this.numberOfRelatedComics = characterComics.getNumberOfComics();
        this.relatedComics = characterComics.getComics();
        this.relatedURI = new HashMap<>();
        relatedURI.put("allCharactersURI", LinkManager.getAllCharactersURI());
        relatedURI.put("TheCharacterURI", LinkManager.getCharacterURI(this.character.getId()));
        relatedURI.put("CharacterComicsURI", LinkManager.getCharacterComicsURI(this.character.getId()));
    }

    public Character getCharacter() {
        return character;
    }

//    public ComicsCollectionResRepr getCharacterComics() {
//        return characterComics;
//    }

    public int getNumberOfRelatedComics() {
        return numberOfRelatedComics;
    }

    public List<ComicShortView> getRelatedComics() {
        return relatedComics;
    }

    public Map<String, URI> getRelatedURI() {
        return relatedURI;
    }
}
