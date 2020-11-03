package com.agubin.cards.models;

import com.agubin.cards.utils.Orderly;

import javax.persistence.*;


@Entity
@Table(name = "chars")
public class Character implements Orderly {

    @Id
    @SequenceGenerator(name = "chars_seq", sequenceName = "chars_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chars_seq")
    private Long id;

    private String name;
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character() {
    }

    public Character(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String nameLikeValue() {
        return getName();
    }

    @Override
    public Long idLikeValue() {
        return getId();
    }
}