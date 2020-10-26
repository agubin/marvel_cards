package com.agubin.cards.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "char_comics")
public class CharacterComics {

    @Id
    private int id;
    private Long charId;
    private Long comicsId;

    public CharacterComics() {
    }

    public CharacterComics(Long charId, Long comicsId) {
        this.charId = charId;
        this.comicsId = comicsId;
    }

    public int getId() {
        return id;
    }

    public Long getCharId() {
        return charId;
    }

    public void setCharId(Long charId) {
        this.charId = charId;
    }

    public Long getComicsId() {
        return comicsId;
    }

    public void setComicsId(Long comicsId) {
        this.comicsId = comicsId;
    }
}
