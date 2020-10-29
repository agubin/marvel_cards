package com.agubin.cards.services;

import com.agubin.cards.exceptions.InvalidEntityException;
import com.agubin.cards.exceptions.NonExistingCharacterException;
import com.agubin.cards.models.Character;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CharacterService {

    List<Character> getCharacters(Map<String, String> allQueryParams);

    Optional<Character> createCharacter(Character character) throws InvalidEntityException;

    Optional<Character> updateCharacter(Character character);

    boolean deleteCharacter(Long characterId);

    List<Character> getComicsCharacters(Long id, Map<String, String> allQueryParams);

    boolean writeDownFile(MultipartFile file, Long characterId);

    Optional<byte[]> getImageById(Long characterId);

    boolean updateFile(MultipartFile file, Long characterId);

    Optional<Character> getCharacterById(Long characterId);

    void bindCharactersToComic(Long comicId, List<Long> charactersId) throws NonExistingCharacterException;

    void unbindCharactersFromComic(Long comicId, List<Long> charactersId) throws NonExistingCharacterException;
}
