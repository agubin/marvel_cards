package com.agubin.cards.services;

import com.agubin.cards.exceptions.NonExistingCharacterException;
import com.agubin.cards.exceptions.ResourceNotFoundException;
import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;

import java.util.List;
import java.util.Map;

public interface CharacterComicService {

    List<Character> getComicCharacters(Long comicId, Map<String, String> allQueryParams) throws ResourceNotFoundException;

    void bindCharactersToComic(Long comicId, List<Long> charactersId) throws ResourceNotFoundException, NonExistingCharacterException;

    void unbindCharactersFromComic(Long comicId, List<Long> charactersId) throws ResourceNotFoundException, NonExistingCharacterException;

    List<Comics> getCharacterComics(Long characterId, Map<String, String> allQueryParams) throws ResourceNotFoundException;

    void bindComicsToCharacter(Long characterId, List<Long> comicsId) throws ResourceNotFoundException, NonExistingCharacterException;

    void unbindComicsFromCharacter(Long characterId, List<Long> comicsId) throws ResourceNotFoundException, NonExistingCharacterException;
}
