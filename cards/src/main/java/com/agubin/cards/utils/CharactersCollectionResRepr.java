package com.agubin.cards.utils;

import com.agubin.cards.models.Character;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CharactersCollectionResRepr {

    private int numberOfCharacters;
    private List<CharacterShortView> characters;
    private Map<String, URI> relatedURI;

    public CharactersCollectionResRepr(List<Character> characterList) {
        this.numberOfCharacters = characterList.size();
        this.characters = characterList.stream().map(CharacterShortView::new).collect(Collectors.toList());
        this.relatedURI = new HashMap<>();
        this.relatedURI.put("allCharactersURI", LinkManager.getAllCharactersURI());
        this.relatedURI.put("allComicsURI", LinkManager.getAllComicsURI());
    }

    public CharactersCollectionResRepr(List<Character> characterList, Long comicId) {
        this.numberOfCharacters = characterList.size();
        this.characters = characterList.stream().map(CharacterShortView::new).collect(Collectors.toList());
        this.relatedURI = new HashMap<>();
        this.relatedURI.put("allCharactersURI", LinkManager.getAllCharactersURI());
        this.relatedURI.put("allComicsURI", LinkManager.getAllComicsURI());
        this.relatedURI.put("TheComicCharactersURI", LinkManager.getComicCharactersURI(comicId));
    }

//    public String getAllCharactersLink() {
//        return allCharactersLink;
//    }

//    public String getSelfLink() {
//        return selfLink;
//    }

    public int getNumberOfCharacters() {
        return numberOfCharacters;
    }

    public List<CharacterShortView> getCharacters() {
        return characters;
    }

    public Map<String, URI> getRelatedURI() {
        return relatedURI;
    }
}
