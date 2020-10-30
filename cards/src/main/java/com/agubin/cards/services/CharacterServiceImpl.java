package com.agubin.cards.services;

import com.agubin.cards.exceptions.*;
import com.agubin.cards.models.Character;
import com.agubin.cards.models.CharacterComics;
import com.agubin.cards.repo.CharacterComicsRepository;
import com.agubin.cards.repo.CharacterRepository;
import com.agubin.cards.utils.FileHandler;
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
        if (characters.isEmpty()) {
            throw new UnexpectedBehaviourException();
        }
        characters = (List<Character>) SortFilter.sortAndFilter(characters, allQueryParams);

        return characters;
    }

    @Override
    public Character getCharacterById(Long characterId) {
        Optional<Character> character = characterRepository.findById(characterId);
        if (!character.isPresent()) {
            throw new ResourceNotFoundException(ResourceTypes.CHR, characterId);
        }
        return character.get();
    }

    private Character saveCharacterEntity(Character character) {
        Character result = characterRepository.save(character);
        return  result;
    }

    private void checkCharacterEntity(Character character) throws InvalidEntityException {
        StringBuffer errorMessage = new StringBuffer();
        if (character.getName() == null) {
            errorMessage.append("Name field is required!");
        }
        if (character.getDescription() == null) {
            errorMessage.append("Description field is required!");
        }
        throw new InvalidEntityException(errorMessage.toString());
    }

    @Override
    public Character createCharacter(Character character) throws InvalidEntityException {
        checkCharacterEntity(character);
        return saveCharacterEntity(character);
    }

    @Override
    public Character updateCharacter(Long characterId, Character character) throws ResourceNotFoundException {
        Optional<Character> characterObj = characterRepository.findById(character.getId());
        if (!characterObj.isPresent()) {
            throw new ResourceNotFoundException(ResourceTypes.CHR, characterId);
        }
        Character updatedCharacter = characterObj.get();
        if (character.getName() != null) {
            updatedCharacter.setName(character.getName());
        }
        if (character.getDescription() != null) {
            updatedCharacter.setDescription(character.getDescription());
        }
        return saveCharacterEntity(character);
    }

    @Override
    public void deleteCharacter(Long characterId) throws ResourceNotFoundException {
        if (!characterRepository.existsById(characterId)) {
            throw new ResourceNotFoundException(ResourceTypes.CHR, characterId);
        }
        try {
            characterRepository.deleteById(characterId);
        } catch (Exception exception) {
            throw new UnexpectedBehaviourException();
        }
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


    private void writeFile(MultipartFile file, Long characterId) {
        if (file.isEmpty()) {
            throw new InvalidDataException(DataCorruptionTypes.EMPTY_FILE);
        }
        try (BufferedOutputStream bous = new BufferedOutputStream(new FileOutputStream(new File(FileHandler.getPortraitFileName(characterId))))) {
            bous.write(file.getBytes());
        } catch (IOException ignored) {
            throw new UnexpectedBehaviourException();
        }
    }

    private boolean characterPortraitExist(Long characterId) {
        return new File(FileHandler.getPortraitFileName(characterId)).exists();
    }

    @Override
    public void writeDownFile(MultipartFile file, Long characterId) {
        if (characterPortraitExist(characterId)) {
            throw new ResourceAlreadyExistsException(ResourceTypes.IMG, characterId);
        }
        writeFile(file, characterId);
    }

    @Override
    public void updateFile(MultipartFile file, Long characterId) {
        if (!characterPortraitExist(characterId)) {
            throw new ResourceNotFoundException(ResourceTypes.IMG, characterId);
        }
        writeFile(file, characterId);
    }

    @Override
    public byte[] getImageById(Long characterId) {
        if (!characterPortraitExist(characterId)) {
            throw new ResourceNotFoundException(ResourceTypes.IMG, characterId);
        }
        byte[] bImage;
        try {
            bImage = Files.readAllBytes(Paths.get(FileHandler.getPortraitFileName(characterId)));
        } catch (IOException ignored) {
            throw new UnexpectedBehaviourException();
        }
        if (bImage.length == 0) {
            throw new UnexpectedBehaviourException();
        }
        return bImage;
    }
}
