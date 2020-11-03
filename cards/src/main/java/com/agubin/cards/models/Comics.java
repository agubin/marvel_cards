package com.agubin.cards.models;


import com.agubin.cards.utils.Orderly;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Table (name = "comics")
public class Comics implements Orderly {

    @Id
    @SequenceGenerator(name = "comics_seq", sequenceName = "comics_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comics_seq")
    private Long id;

    private String title;
    private String description;
    private String creators;

    public String getCreators() {
        return creators;
    }

    public void setCreators(String creators) {
        this.creators = creators;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Comics() {
    }

    public Comics(Long id, String name, String description, String creators) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.creators = creators;
    }

    @Override
    public String nameLikeValue() {
        return getTitle();
    }

    @Override
    public Long idLikeValue() {
        return getId();
    }
}
