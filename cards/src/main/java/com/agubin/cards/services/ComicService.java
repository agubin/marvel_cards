package com.agubin.cards.services;

import com.agubin.cards.models.Comics;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ComicService {
    List<Comics> getComics(Map<String, String> allQueryParams);

    boolean createComic(Comics comic);

    boolean updateComic(Comics comic);

    Optional<Comics> getComicById(Long comicId);

    boolean deleteComic(Long comicId);

    List<Comics> getCharacterComics(Long characterId, Map<String, String> allQueryParams);

    boolean bindComicsToCharacter(Long characterId, List<Long> comicsId);

    boolean unbindComicsFromCharacter(Long characterId, List<Long> comicsId);
}
