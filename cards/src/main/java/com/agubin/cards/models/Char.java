package com.agubin.cards.models;

import org.apache.logging.log4j.util.PropertySource;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Table(name = "chars")
public class Char {

    @Id
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

    public Char() {
    }

    public Char(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

//    public Char(Char character) {
//        System.out.println("Special constructor worked");
//        this.id = character.getId();
//        this.name = character.getName();
//        this.description = character.getDescription();
//    }

    public static Comparator<Char> getComparatorFor(String condition) {
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
}