package com.agubin.cards.controllers;

import com.agubin.cards.exceptions.InvalidEntityException;
import com.agubin.cards.exceptions.NonExistingCharacterException;
import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;
import com.agubin.cards.services.CharacterService;
import com.agubin.cards.services.ComicService;
import com.agubin.cards.utils.CharactersCollectionResRepr;
import com.agubin.cards.utils.LinkManager;
import com.agubin.cards.utils.PersonalCharacterResRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;
    @Autowired
    private ComicService comicService;

    @GetMapping("/characters")
    public ResponseEntity<CharactersCollectionResRepr> getCharacters(@RequestParam Map<String, String> allQueryParams) {
        List<Character> characters = characterService.getCharacters(allQueryParams);
        return !characters.isEmpty()
                ? new ResponseEntity<>(new CharactersCollectionResRepr(characters), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/characters")
    public ResponseEntity<?> postCharacter(@RequestBody Character character) {
        try {
            Optional<Character> createdCharacter = characterService.createCharacter(character);
            if (createdCharacter.isPresent()) {
                List<Comics> comics = comicService.getCharacterComics(createdCharacter.get().getId(), new HashMap<>());
                return ResponseEntity
                        .created(LinkManager.getCharacterURI(createdCharacter.get().getId()))
                        .body(new PersonalCharacterResRepr(createdCharacter.get(), comics));
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidEntityException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/characters/{characterid}")
    public ResponseEntity<?> updateCharacter(@PathVariable(value = "characterId") Long characterId, @RequestBody Character character) {
        character.setId(characterId);
        Optional<Character> updatedCharacter = characterService.updateCharacter(character);
        if (updatedCharacter.isPresent()) {
            List<Comics> comics = comicService.getCharacterComics(updatedCharacter.get().getId(), new HashMap<>());
            return new ResponseEntity<>(new PersonalCharacterResRepr(updatedCharacter.get(), comics), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/characters/{characterid}")
    public ResponseEntity<PersonalCharacterResRepr> getCharacter(@PathVariable(value = "characterid") Long characterId) {
        Optional<Character> character = characterService.getCharacterById(characterId);
        if (character.isPresent()) {
            List<Comics> comics = comicService.getCharacterComics(characterId, new HashMap<>());
            return new ResponseEntity<>(new PersonalCharacterResRepr(character.get(), comics), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/characters/{characterid}")
    public ResponseEntity<?> deleteCharacter(@PathVariable(value = "characterid") Long characterId) {
        boolean isCharacterDeleted = characterService.deleteCharacter(characterId);
        return isCharacterDeleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/comics/{comicid}/characters")
    public ResponseEntity<CharactersCollectionResRepr> getComicsCharacters(@RequestParam Map<String, String> allQueryParams,
                                                               @PathVariable(value = "comicid") Long comicId) {
        List<Character> characters = characterService.getComicsCharacters(comicId, allQueryParams);
        return !characters.isEmpty()
                ? new ResponseEntity<>(new CharactersCollectionResRepr(characters, comicId), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("comics/{comicid}/characters")
    public ResponseEntity<?> bindCharacters(@PathVariable(value = "comicid") Long comicId, @RequestBody List<Long> charactersId) {
        try {
            characterService.bindCharactersToComic(comicId, charactersId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NonExistingCharacterException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("comics/{comicid}/characters/{charactersid}")
    public ResponseEntity<?> unbindCharacters(@PathVariable(value = "comicid") Long comicId, @PathVariable(value = "charactersid") List<Long> charactersId) {
        try {
            characterService.unbindCharactersFromComic(comicId, charactersId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NonExistingCharacterException exception) {
            return new ResponseEntity<>(exception.getErrorMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("characters/{characterid}/portrait")
    public ResponseEntity<?> uploadImage(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        boolean isFileWritten = characterService.writeDownFile(file, characterId);
        return isFileWritten
                ? ResponseEntity.created(LinkManager.getCharacterPortraitURI(characterId)).build()
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("characters/{characterid}/portrait")
    public ResponseEntity<?> updateImage(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        boolean isFileUpdated = characterService.updateFile(file, characterId);
        return isFileUpdated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/characters/{characterid}/portrait")
    public ResponseEntity<?> sendImage(@PathVariable(value = "characterid") Long characterId) {
        Optional<byte[]> Image = characterService.getImageById(characterId);
        if (Image.isPresent()) {
            byte[] bImage = Image.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentLength(bImage.length).body(new InputStreamResource(new ByteArrayInputStream(bImage)));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
