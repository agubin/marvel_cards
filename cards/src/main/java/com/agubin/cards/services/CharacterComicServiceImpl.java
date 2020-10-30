package com.agubin.cards.services;

import com.agubin.cards.exceptions.*;
import com.agubin.cards.models.Character;
import com.agubin.cards.models.CharacterComics;
import com.agubin.cards.models.Comics;
import com.agubin.cards.repo.CharacterComicsRepository;
import com.agubin.cards.repo.CharacterRepository;
import com.agubin.cards.repo.ComicsRepository;
import com.agubin.cards.utils.SortFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CharacterComicServiceImpl implements CharacterComicService {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private ComicsRepository comicsRepository;
    @Autowired
    private CharacterComicsRepository characterComicsRepository;


    @Override
    public List<Character> getComicCharacters(Long comicId, Map<String, String> allQueryParams) throws ResourceNotFoundException {
        checkResourceExisting(comicsRepository, comicId, ResourceTypes.COM);
        List<Character> comicCharacters = new ArrayList<>();
        for (CharacterComics cc : characterComicsRepository.findByComicsId(comicId)) {
            comicCharacters.add(characterRepository.findById(cc.getCharId()).get());
        }
        if (comicCharacters.isEmpty()) {
            throw new UnexpectedBehaviourException();
        }
        comicCharacters = (List<Character>) SortFilter.sortAndFilter(comicCharacters, allQueryParams);
        return comicCharacters;
    }

    @Override
    public void bindCharactersToComic(Long comicId, List<Long> charactersId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(comicsRepository, comicId, ResourceTypes.COM);
        checkResourcesIdList(characterRepository, charactersId, ResourceTypes.CHR);
        for (Long characterId : charactersId) {
            createCharacterComic(characterId, comicId);
        }
    }

    @Override
    public void unbindCharactersFromComic(Long comicId, List<Long> charactersId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(comicsRepository, comicId, ResourceTypes.COM);
        checkResourcesIdList(characterRepository, charactersId, ResourceTypes.CHR);
        for (Long characterId : charactersId) {
            deleteCharacterComic(characterId, comicId);
        }
    }

    @Override
    public List<Comics> getCharacterComics(Long characterId, Map<String, String> allQueryParams) throws ResourceNotFoundException {
        checkResourceExisting(characterRepository, characterId, ResourceTypes.CHR);
        List<Comics> characterComics = new ArrayList<>();
        for (CharacterComics cc: characterComicsRepository.findByCharId(characterId)) {
            characterComics.add(comicsRepository.findById(cc.getComicsId()).get());
        }
        if (characterComics.isEmpty()) {
            throw new UnexpectedBehaviourException();
        }
        characterComics = (List<Comics>) SortFilter.sortAndFilter(characterComics, allQueryParams);
        return characterComics;
    }

    @Override
    public void bindComicsToCharacter(Long characterId, List<Long> comicsId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(characterRepository, characterId, ResourceTypes.CHR);
        checkResourcesIdList(comicsRepository, comicsId, ResourceTypes.COM);
        for (Long comicId : comicsId) {
            createCharacterComic(characterId, comicId);
        }
    }

    @Override
    public void unbindComicsFromCharacter(Long characterId, List<Long> comicsId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(characterRepository, characterId, ResourceTypes.CHR);
        checkResourcesIdList(comicsRepository, comicsId, ResourceTypes.COM);
        for (Long comicId : comicsId) {
            deleteCharacterComic(characterId, comicId);
        }
    }

    private void checkResourcesIdList(CrudRepository<?, Long> repository, List<Long> resourcesId, String resourceType) throws NonExistingCharacterException {
        ArrayList<Long> nonExistingResourcesId = new ArrayList<>();
        for (Long resourceId: resourcesId) {
            if (!repository.existsById(resourceId)) {
                nonExistingResourcesId.add(resourceId);
            }
        }
        if (!nonExistingResourcesId.isEmpty()) {
            throw new ResourceNotFoundException(resourceType, nonExistingResourcesId);
        }
    }

    private void checkResourceExisting(CrudRepository<?, Long> repository, Long resourceId, String resourceType) throws ResourceNotFoundException {
        if (!repository.existsById(resourceId)) {
            throw new ResourceNotFoundException(resourceType, resourceId);
        }
    }

    private void createCharacterComic(Long characterId, Long comicId) {
        if (characterComicsRepository.findByCharIdAndComicsId(characterId, comicId).isPresent()) {
            throw new ResourceAlreadyExistsException(ResourceTypes.CHR_COM, characterId, comicId);
        }
        characterComicsRepository.save(new CharacterComics(characterId, comicId));
    }

    private void deleteCharacterComic(Long characterId, Long comicId) {
        Optional<CharacterComics> characterComics = characterComicsRepository.findByCharIdAndComicsId(characterId, comicId);
        if (!characterComics.isPresent()) {
            throw new ResourceNotFoundException(ResourceTypes.CHR_COM, characterId, comicId);
        }
        characterComicsRepository.delete(characterComics.get());
    }
}
