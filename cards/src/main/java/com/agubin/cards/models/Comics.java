package com.agubin.cards.models;


import com.agubin.cards.utils.Orderly;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Comparator;

@Entity
@Table (name = "comics")
public class Comics implements Orderly {

    @Id
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
    public Comparator<com.agubin.cards.models.Comics> getComparatorFor(String condition) {
        return getComparator(condition);
    }

    @Override
    public String nameLikeValue() {
        return getTitle();
    }

    @Override
    public Long idLikeValue() {
        return getId();
    }

    public static Comparator<Comics> getComparator(String condition) {
        switch (condition) {
            case "title":
                return (o1, o2) -> o1.getTitle().compareTo(o2.getTitle());
            case "-title":
                return (o1, o2) -> o2.getTitle().compareTo(o1.getTitle());
            case "id":
                return (o1, o2) -> o1.getId().compareTo(o2.getId());
            case "-id":
                return (o1, o2) -> o2.getId().compareTo(o1.getId());
            default:
                return (o1, o2) -> 0;
        }
    }
}
