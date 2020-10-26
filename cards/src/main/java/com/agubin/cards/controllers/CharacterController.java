package com.agubin.cards.controllers;

import com.agubin.cards.models.Character;
import com.agubin.cards.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/characters")
    public ResponseEntity<List<Character>> getCharacters(@RequestParam Map<String, String> allQueryParams) {
        List<Character> characters =characterService.getCharacters(allQueryParams);
        return !characters.isEmpty()
                ? new ResponseEntity<>(characters, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/characters")
    public ResponseEntity<?> postCharacter(@RequestBody Character character) {
        boolean isCharacterCreated = characterService.createCharacter(character);
        return isCharacterCreated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/characters")
    public ResponseEntity<?> updateCharacter(@RequestBody Character character) {
        boolean isCharacterUpdated = characterService.updateCharacter(character);
        return isCharacterUpdated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/characters/{characterid}")
    public ResponseEntity<Character> getCharacter(@PathVariable(value = "characterid") Long characterId) {
        Optional<Character> character = characterService.getCharacterById(characterId);
        return character.isPresent()
                ? new ResponseEntity<>(character.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/characters/{characterid}")
    public ResponseEntity<?> deleteCharacter(@PathVariable(value = "characterid") Long characterId) {
        boolean isCharacterDeleted = characterService.deleteCharacter(characterId);
        return isCharacterDeleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/comics/{comicid}/character")
    public ResponseEntity<List<Character>> getComicsCharacters(@RequestParam Map<String, String> allQueryParams,
                                                               @PathVariable(value = "comicid") Long comicId) {
        List<Character> characters =characterService.getComicsCharacters(comicId, allQueryParams);
        return !characters.isEmpty()
                ? new ResponseEntity<>(characters, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("characters/{characterid}/picture")
    public ResponseEntity<?> uploadImage(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        boolean isFileWritten = characterService.writeDownFile(file, characterId);
        return isFileWritten
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("characters/{characterid}/picture")
    public ResponseEntity<?> updateImage(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        boolean isFileUpdated = characterService.updateFile(file, characterId);
        return isFileUpdated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/characters/{characterid}/picture")
    public ResponseEntity<?> sendImage(@PathVariable(value = "characterid") Long characterId) {
        Optional<byte[]> Image = characterService.getImageById(characterId);
        if (Image.isPresent()) {
            byte[] bImage = Image.get();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentLength(bImage.length).body(new InputStreamResource(new ByteArrayInputStream(bImage)));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
