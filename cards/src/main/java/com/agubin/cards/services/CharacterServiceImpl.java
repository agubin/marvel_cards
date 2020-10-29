package com.agubin.cards.services;

import com.agubin.cards.exceptions.InvalidEntityException;
import com.agubin.cards.exceptions.NonExistingCharacterException;
import com.agubin.cards.models.Character;
import com.agubin.cards.models.CharacterComics;
import com.agubin.cards.repo.CharacterComicsRepository;
import com.agubin.cards.repo.CharacterRepository;
import com.agubin.cards.utils.SortFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    private Optional<Character> saveAndCheckCharacterEntity(Character character) {
        Character result = characterRepository.save(character);
        return result.getId().equals(character.getId())
                && result.getName().equals(character.getName())
                && result.getDescription().equals(character.getDescription())
                ? Optional.of(result)
                : Optional.empty();
    }

    private void checkCharacterEntity(Character character) throws InvalidEntityException {
        StringBuffer errorMessage = new StringBuffer();
        if (character.getName() == null) {
            errorMessage.append("Name filed is required!");
        }
        if (character.getDescription() == null) {
            errorMessage.append("Description field is required!");
        }
        throw new InvalidEntityException(errorMessage.toString());
    }

    @Override
    public Optional<Character> createCharacter(Character character) throws InvalidEntityException {
        checkCharacterEntity(character);
        return saveAndCheckCharacterEntity(character);
    }

    @Override
    public Optional<Character> updateCharacter(Character character) {
        Optional<Character> characterStub = characterRepository.findById(character.getId());
        if (characterStub.isPresent()) {
            Character updatedCharacter = characterStub.get();
            if (character.getName() != null) {
                updatedCharacter.setName(character.getName());
            }
            if (character.getDescription() != null) {
                updatedCharacter.setDescription(character.getDescription());
            }
            return saveAndCheckCharacterEntity(character);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteCharacter(Long characterId) {
        if (characterRepository.existsById(characterId)) {
            characterRepository.deleteById(characterId);
            return true;
        }
        return false;
    }

    @Override
    public List<Character> getComicsCharacters(Long comicId, Map<String, String> allQueryParams) {
        List<Character> comicsCharacters = new ArrayList<>();
        for (CharacterComics cc : characterComicsRepository.findByComicsId(comicId)) {
            comicsCharacters.add(characterRepository.findById(cc.getCharId()).get());
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

    private void checkCharactersIdList(List<Long> charactersId) throws NonExistingCharacterException {
        ArrayList<Long> nonExistingCharactersId = new ArrayList<>();
        for (Long characterId: charactersId) {
            if (!characterRepository.existsById(characterId)) {
                nonExistingCharactersId.add(characterId);
            }
        }
        if (!nonExistingCharactersId.isEmpty()) {
            throw new NonExistingCharacterException(nonExistingCharactersId);
        }
    }

    @Override
    public void bindCharactersToComic(Long comicId, List<Long> charactersId) throws NonExistingCharacterException {
        checkCharactersIdList(charactersId);
        for (Long characterId : charactersId) {
            if (!characterComicsRepository.findByCharIdAndComicsId(characterId, comicId).isPresent()) {
                characterComicsRepository.save(new CharacterComics(characterId, comicId));
            }
        }
    }

    @Override
    public void unbindCharactersFromComic(Long comicId, List<Long> charactersId) throws NonExistingCharacterException {
        checkCharactersIdList(charactersId);
        for (Long characterId : charactersId) {
            Optional<CharacterComics> characterComics = characterComicsRepository.findByCharIdAndComicsId(characterId, comicId);
            characterComics.ifPresent(charComics -> characterComicsRepository.delete(charComics));
        }
    }
}
