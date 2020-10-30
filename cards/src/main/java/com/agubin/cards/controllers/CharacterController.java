package com.agubin.cards.controllers;

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

@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;
    @Autowired
    private ComicService comicService;


    //Реализовать оптимизированную выборку из БД
    @GetMapping("/characters")
    public ResponseEntity<CharactersCollectionResRepr> getCharacters(@RequestParam Map<String, String> allQueryParams) {
        List<Character> characters = characterService.getCharacters(allQueryParams);
        return new ResponseEntity<>(new CharactersCollectionResRepr(characters), HttpStatus.OK);
    }

    @PostMapping("/characters")
    public ResponseEntity<?> postCharacter(@RequestBody Character character) {
        Character createdCharacter = characterService.createCharacter(character);
        List<Comics> comics = comicService.getCharacterComics(createdCharacter.getId(), new HashMap<>());
        return ResponseEntity
                .created(LinkManager.getCharacterURI(createdCharacter.getId()))
                .body(new PersonalCharacterResRepr(createdCharacter, comics));
    }

    @PutMapping("/characters/{characterid}")
    public ResponseEntity<?> updateCharacter(@PathVariable(value = "characterId") Long characterId, @RequestBody Character character) {
        Character updatedCharacter = characterService.updateCharacter(characterId, character);
        List<Comics> comics = comicService.getCharacterComics(updatedCharacter.getId(), new HashMap<>());
        return new ResponseEntity<>(new PersonalCharacterResRepr(updatedCharacter, comics), HttpStatus.CREATED);
    }

    @GetMapping("/characters/{characterid}")
    public ResponseEntity<PersonalCharacterResRepr> getCharacter(@PathVariable(value = "characterid") Long characterId) {
        Character character = characterService.getCharacterById(characterId);
        List<Comics> comics = comicService.getCharacterComics(characterId, new HashMap<>());
        return new ResponseEntity<>(new PersonalCharacterResRepr(character, comics), HttpStatus.OK);
    }

    @DeleteMapping("/characters/{characterid}")
    public ResponseEntity<?> deleteCharacter(@PathVariable(value = "characterid") Long characterId) {
        characterService.deleteCharacter(characterId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("characters/{characterid}/portrait")
    public ResponseEntity<?> uploadImage(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        characterService.writeDownFile(file, characterId);
        return ResponseEntity.created(LinkManager.getCharacterPortraitURI(characterId)).build();
    }

    @PutMapping("characters/{characterid}/portrait")
    public ResponseEntity<?> updateImage(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        characterService.updateFile(file, characterId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/characters/{characterid}/portrait")
    public ResponseEntity<?> sendImage(@PathVariable(value = "characterid") Long characterId) {
        byte[] bImage = characterService.getImageById(characterId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentLength(bImage.length).body(new InputStreamResource(new ByteArrayInputStream(bImage)));
    }
}
