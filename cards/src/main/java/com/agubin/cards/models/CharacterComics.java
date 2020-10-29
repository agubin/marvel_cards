package com.agubin.cards.models;

import javax.persistence.*;

@Entity
@Table(name = "char_comics")
public class CharacterComics {

    @Id
    @SequenceGenerator(name = "char_comics_seq", sequenceName = "char_comics_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "char_comics_seq")
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
