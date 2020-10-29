package com.agubin.cards.services;

import com.agubin.cards.exceptions.NonExistingCharacterException;
import com.agubin.cards.exceptions.ResourceNotFoundException;
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
        checkResourceExisting(comicsRepository, comicId, "Comic");
        List<Character> comicsCharacters = new ArrayList<>();
        for (CharacterComics cc : characterComicsRepository.findByComicsId(comicId)) {
            comicsCharacters.add(characterRepository.findById(cc.getCharId()).get());
        }
        comicsCharacters = (List<Character>) SortFilter.sortAndFilter(comicsCharacters, allQueryParams);
        return comicsCharacters;
    }

    @Override
    public void bindCharactersToComic(Long comicId, List<Long> charactersId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(comicsRepository, comicId, "Comic");
        checkResourcesIdList(characterRepository, charactersId);
        for (Long characterId : charactersId) {
            if (!characterComicsRepository.findByCharIdAndComicsId(characterId, comicId).isPresent()) {
                characterComicsRepository.save(new CharacterComics(characterId, comicId));
            }
        }
    }

    @Override
    public void unbindCharactersFromComic(Long comicId, List<Long> charactersId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(comicsRepository, comicId, "Comic");
        checkResourcesIdList(characterRepository, charactersId);
        for (Long characterId : charactersId) {
            Optional<CharacterComics> characterComics = characterComicsRepository.findByCharIdAndComicsId(characterId, comicId);
            characterComics.ifPresent(charComics -> characterComicsRepository.delete(charComics));
        }
    }

    @Override
    public List<Comics> getCharacterComics(Long characterId, Map<String, String> allQueryParams) throws ResourceNotFoundException {
        checkResourceExisting(characterRepository, characterId, "Character");
        List<Comics> characterComics = new ArrayList<>();
        for (CharacterComics cc: characterComicsRepository.findByCharId(characterId)) {
            characterComics.add(comicsRepository.findById(cc.getComicsId()).get());
        }
        characterComics = (List<Comics>) SortFilter.sortAndFilter(characterComics, allQueryParams);
        return characterComics;
    }

    @Override
    public void bindComicsToCharacter(Long characterId, List<Long> comicsId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(characterRepository, characterId, "Character");
        checkResourcesIdList(comicsRepository, comicsId);
        for (Long comicId : comicsId) {
            if (!characterComicsRepository.findByCharIdAndComicsId(characterId, comicId).isPresent()) {
                characterComicsRepository.save(new CharacterComics(characterId, comicId));
            }
        }
    }

    @Override
    public void unbindComicsFromCharacter(Long characterId, List<Long> comicsId) throws ResourceNotFoundException, NonExistingCharacterException {
        checkResourceExisting(characterRepository, characterId, "Character");
        checkResourcesIdList(comicsRepository, comicsId);
        for (Long comicId : comicsId) {
            Optional<CharacterComics> characterComics = characterComicsRepository.findByCharIdAndComicsId(characterId, comicId);
            characterComics.ifPresent(charComics -> characterComicsRepository.delete(charComics));
        }
    }

    private void checkResourcesIdList(CrudRepository<?, Long> repository, List<Long> resourcesId) throws NonExistingCharacterException {
        ArrayList<Long> nonExistingResourcesId = new ArrayList<>();
        for (Long resourceId: resourcesId) {
            if (!repository.existsById(resourceId)) {
                nonExistingResourcesId.add(resourceId);
            }
        }
        if (!nonExistingResourcesId.isEmpty()) {
            throw new NonExistingCharacterException(nonExistingResourcesId);
        }
    }

    private void checkResourceExisting(CrudRepository<?, Long> repository, Long resourceId, String resourceName) throws ResourceNotFoundException {
        if (!repository.existsById(resourceId)) {
            throw new ResourceNotFoundException(resourceName + " with id=" + resourceId + " not found");
        }
    }

//    private void checkCharactersIdList(List<Long> charactersId) throws NonExistingCharacterException {
//        ArrayList<Long> nonExistingCharactersId = new ArrayList<>();
//        for (Long characterId: charactersId) {
//            if (!characterRepository.existsById(characterId)) {
//                nonExistingCharactersId.add(characterId);
//            }
//        }
//        if (!nonExistingCharactersId.isEmpty()) {
//            throw new NonExistingCharacterException(nonExistingCharactersId);
//        }
//    }
//
//    private void checkComicExisting(Long comicId) throws ResourceNotFoundException{
//        if (!comicsRepository.existsById(comicId)) {
//            throw new ResourceNotFoundException("Comic with id=" + comicId + " not found");
//        }
//    }
//
//    private void checkCharacterExisting(Long characterId) throws ResourceNotFoundException{
//        if (!comicsRepository.existsById(characterId)) {
//            throw new ResourceNotFoundException("Character with id=" + characterId + " not found");
//        }
//    }
}
