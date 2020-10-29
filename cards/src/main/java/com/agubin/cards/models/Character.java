package com.agubin.cards.models;

import com.agubin.cards.utils.LinkManager;
import com.agubin.cards.utils.Orderly;

import javax.persistence.*;
import java.io.File;
import java.net.URI;
import java.util.Comparator;

@Entity
@Table(name = "chars")
public class Character implements Orderly {

    @Id
    @SequenceGenerator(name = "chars_seq", sequenceName = "chars_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chars_seq")
    private Long id;

    private String name;
    private String description;
    @Transient
    private String portraitLink;

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

    public URI getPortraitLink() {
        return new File("character#" + id).exists()
                ? LinkManager.getCharacterPortraitURI(id)
                : null;
    }

    public Character() {
    }

    public Character(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Comparator<Character> getComparator(String condition) {
        switch (condition) {
            case "name":
                return (o1, o2) -> o1.getName().compareTo(o2.getName());
            case "-name":
                return (o1, o2) -> o2.getName().compareTo(o1.getName());
            case "id":
                return (o1, o2) -> o1.getId().compareTo(o2.getId());
            case "-id":
                return (o1, o2) -> o2.getId().compareTo(o1.getId());
            default:
                return (o1, o2) -> 0;
        }
    }

    @Override
    public Comparator<Character> getComparatorFor(String condition) {
        return getComparator(condition);
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