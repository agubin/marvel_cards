package com.agubin.cards.utils.representations;

import com.agubin.cards.models.Character;
import com.agubin.cards.utils.LinkManager;

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
