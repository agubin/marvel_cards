package com.agubin.cards.controllers;

import com.agubin.cards.exceptions.NonExistingCharacterException;
import com.agubin.cards.exceptions.ResourceNotFoundException;
import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;
import com.agubin.cards.services.CharacterComicService;
import com.agubin.cards.utils.CharactersCollectionResRepr;
import com.agubin.cards.utils.ComicsCollectionResRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CharacterComicController {

    @Autowired
    private CharacterComicService characterComicService;

    @GetMapping("/comics/{comicid}/characters")
    public ResponseEntity<?> getComicCharacters(@RequestParam Map<String, String> allQueryParams,
                                                                           @PathVariable(value = "comicid") Long comicId) {
        try {
            List<Character> characters = characterComicService.getComicCharacters(comicId, allQueryParams);
            return new ResponseEntity<>(new CharactersCollectionResRepr(characters, comicId), HttpStatus.OK);
        } catch (ResourceNotFoundException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("comics/{comicid}/characters")
    public ResponseEntity<?> bindCharacters(@PathVariable(value = "comicid") Long comicId, @RequestBody List<Long> charactersId) {
        try {
            characterComicService.bindCharactersToComic(comicId, charactersId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NonExistingCharacterException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("comics/{comicid}/characters/{charactersid}")
    public ResponseEntity<?> unbindCharacters(@PathVariable(value = "comicid") Long comicId, @PathVariable(value = "charactersid") List<Long> charactersId) {
        try {
            characterComicService.unbindCharactersFromComic(comicId, charactersId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonExistingCharacterException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/characters/{characterid}/comics")
    public ResponseEntity<ComicsCollectionResRepr> getCharacterComics(@RequestParam Map<String, String> allQueryParams,
                                                                     @PathVariable(value = "characterid") Long characterId) {
        try {
            List<Comics> comics = characterComicService.getCharacterComics(characterId, allQueryParams);
            return new ResponseEntity<>(new ComicsCollectionResRepr(comics, characterId), HttpStatus.OK);
        } catch (ResourceNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("characters/{characterid}/comics")
    public ResponseEntity<?> bindComics(@PathVariable(value = "characterid") Long characterId, @RequestBody List<Long> comicsId) {
        characterComicService.bindComicsToCharacter(characterId, comicsId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("characters/{characterid}/comics/{comicsid}")
    public ResponseEntity<?> unbindComics(@PathVariable(value = "characterid") Long characterId, @PathVariable(value = "comicsid") List<Long> comicsId) {
        try {
            characterComicService.unbindComicsFromCharacter(characterId, comicsId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonExistingCharacterException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
