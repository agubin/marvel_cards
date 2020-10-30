package com.agubin.cards.services;

import com.agubin.cards.exceptions.InvalidEntityException;
import com.agubin.cards.exceptions.ResourceNotFoundException;
import com.agubin.cards.models.Character;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CharacterService {

    List<Character> getCharacters(Map<String, String> allQueryParams);

    Character createCharacter(Character character) throws InvalidEntityException;

    Character updateCharacter(Long characterId, Character character) throws ResourceNotFoundException;

    void deleteCharacter(Long characterId) throws ResourceNotFoundException;

    List<Character> getComicsCharacters(Long id, Map<String, String> allQueryParams);

    void writeDownFile(MultipartFile file, Long characterId);

    byte[] getImageById(Long characterId);

    void updateFile(MultipartFile file, Long characterId);

    Character getCharacterById(Long characterId);
}
