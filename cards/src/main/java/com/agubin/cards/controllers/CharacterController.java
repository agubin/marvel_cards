package com.agubin.cards.controllers;

import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;
import com.agubin.cards.services.CharacterService;
import com.agubin.cards.services.ComicService;
import com.agubin.cards.utils.representations.CharactersCollectionResRepr;
import com.agubin.cards.utils.LinkManager;
import com.agubin.cards.utils.representations.PersonalCharacterResRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;
    @Autowired
    private ComicService comicService;


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
}
