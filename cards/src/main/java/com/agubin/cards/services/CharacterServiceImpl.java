package com.agubin.cards.services;

import com.agubin.cards.models.Character;
import com.agubin.cards.models.CharacterComics;
import com.agubin.cards.repo.CharacterComicsRepository;
import com.agubin.cards.repo.CharacterRepository;
import com.agubin.cards.utils.SortFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterComicsRepository characterComicsRepository;


    @Override
    public List<Character> getCharacters(Map<String, String> allQueryParams) {
        List<Character> characters = new ArrayList<>();
        characterRepository.findAll().forEach(characters::add);
        characters = (List<Character>) SortFilter.sortAndFilter(characters, allQueryParams);

        return characters;
    }

    @Override
    public Optional<Character> getCharacterById(Long characterId) {
        return characterRepository.findById(characterId);
    }

    private boolean saveAndCheckCharacterEntity(Character character) {
        Character result = characterRepository.save(character);
        return result.getId().equals(character.getId())
                && result.getName().equals(character.getName())
                && result.getDescription().equals(character.getDescription());
    }

    @Override
    public boolean createCharacter(Character character) {
        return saveAndCheckCharacterEntity(character);
    }

    @Override
    public boolean updateCharacter(Character character) {
        return saveAndCheckCharacterEntity(character);
    }

    @Override
    public boolean deleteCharacter(Long characterId) {
        characterRepository.deleteById(characterId);
        return !characterRepository.findById(characterId).isPresent();
    }

    @Override
    public List<Character> getComicsCharacters(Long comicId, Map<String, String> allQueryParams) {
        List<Character> comicsCharacters = new ArrayList<>();
        for (CharacterComics cc : characterComicsRepository.findByComicsId(comicId)) {
            Optional<Character> character = characterRepository.findById(cc.getCharId());
            character.ifPresent(comicsCharacters::add);
        }
        comicsCharacters = (List<Character>) SortFilter.sortAndFilter(comicsCharacters, allQueryParams);
        return comicsCharacters;
    }

    private boolean writeFile(MultipartFile file, Long characterId) {
        if (!file.isEmpty() && !new File("character#" + characterId).exists()) {
            try (BufferedOutputStream bous = new BufferedOutputStream(new FileOutputStream(new File("character#" + characterId)))) {
                bous.write(file.getBytes());
                return true;
            } catch (IOException ignored) {
            }
        }
        return false;
    }

    @Override
    public boolean writeDownFile(MultipartFile file, Long characterId) {
        return !new File("character#" + characterId).exists() && writeFile(file, characterId);
    }

    @Override
    public boolean updateFile(MultipartFile file, Long characterId) {
        return new File("character#" + characterId).exists() && writeFile(file, characterId);
    }

    @Override
    public Optional<byte[]> getImageById(Long characterId) {
        try {
            byte[] bImage = Files.readAllBytes(Paths.get("character#" + characterId));
            return Optional.of(bImage);
        } catch (IOException ignored) {
        }
        return Optional.empty();
    }
}
