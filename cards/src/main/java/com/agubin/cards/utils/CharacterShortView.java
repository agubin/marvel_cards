package com.agubin.cards.utils;

import com.agubin.cards.models.Character;

import java.net.URI;

public class CharacterShortView {

    private String name;
    private URI characterURI;

    public CharacterShortView(Character character) {
        this.name = character.getName();
        this.characterURI = LinkManager.getCharacterURI(character.getId());
    }

    public String getName() {
        return name;
    }

    public URI getCharacterURI() {
        return characterURI;
    }
}
