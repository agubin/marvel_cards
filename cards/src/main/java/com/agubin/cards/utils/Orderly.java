package com.agubin.cards.utils;


import java.util.Comparator;


public interface Orderly {

    <T> Comparator<T> getComparatorFor(String condition);

    String nameLikeValue();

    Long idLikeValue();
 }

