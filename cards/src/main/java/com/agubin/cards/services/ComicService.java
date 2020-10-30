package com.agubin.cards.services;

import com.agubin.cards.models.Comics;

import java.util.List;
import java.util.Map;

public interface ComicService {
    List<Comics> getComics(Map<String, String> allQueryParams);

    Comics createComic(Comics comic);

    Comics updateComic(Long comicId, Comics comic);

    Comics getComicById(Long comicId);

    void deleteComic(Long comicId);

    List<Comics> getCharacterComics(Long characterId, Map<String, String> allQueryParams);
}
