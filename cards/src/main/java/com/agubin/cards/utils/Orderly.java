package com.agubin.cards.utils;


import java.util.Comparator;

//Provide data that is needed to perform sorting and filtering
public interface Orderly {

    String nameLikeValue();

    Long idLikeValue();

    static Comparator<Orderly> getComparator(String condition) {
        switch (condition) {
            case "name":
                return (o1, o2) -> o1.nameLikeValue().compareTo(o2.nameLikeValue());
            case "-name":
                return (o1, o2) -> o2.nameLikeValue().compareTo(o1.nameLikeValue());
            case "id":
                return (o1, o2) -> o1.idLikeValue().compareTo(o2.idLikeValue());
            case "-id":
                return (o1, o2) -> o2.idLikeValue().compareTo(o1.idLikeValue());
            default:
                return (o1, o2) -> 0;
        }
    }
 }

