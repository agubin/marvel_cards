package com.agubin.cards.services;

import com.agubin.cards.models.Char;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CharacterService {

    List<Char> getCharacters();

    boolean createCharacter(Char character);

    boolean updateCharacter(Char character);

    boolean deleteCharacter(Long characterId);

    List<Char> getComicsCharacters(Long id);

    boolean writeDownFile(MultipartFile file, Long characterId);

    Optional<byte[]> getImageById(Long characterId);

    boolean updateFile(MultipartFile file, Long characterId);

    Optional<Char> getCharacterById(Long characterId);
}
