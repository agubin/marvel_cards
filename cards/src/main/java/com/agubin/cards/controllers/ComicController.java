package com.agubin.cards.controllers;


import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;
import com.agubin.cards.services.CharacterService;
import com.agubin.cards.services.ComicService;
import com.agubin.cards.utils.representations.ComicsCollectionResRepr;
import com.agubin.cards.utils.LinkManager;
import com.agubin.cards.utils.representations.PersonalComicResRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Handle requests related to comics
@Controller
public class ComicController {


    @Autowired
    private ComicService comicService;
    @Autowired
    private CharacterService characterService;

    @GetMapping("/comics")
    public ResponseEntity<ComicsCollectionResRepr> getComics(@RequestParam Map<String, String> allQueryParams) {
        List<Comics> comics = comicService.getComics(allQueryParams);
        return new ResponseEntity<>(new ComicsCollectionResRepr(comics), HttpStatus.OK);
    }

    @PostMapping("/comics")
    public ResponseEntity<?> postComic(@RequestBody Comics comic) {
        Comics createdComic = comicService.createComic(comic);
        List<Character> characters = characterService.getComicsCharacters(createdComic.getId(), new HashMap<>());
        return ResponseEntity
                .created(LinkManager.getCharacterURI(createdComic.getId()))
                .body(new PersonalComicResRepr(createdComic, characters));
    }

    @PutMapping("/comics/{comicid}")
    public ResponseEntity<?> updateComic(@PathVariable(value = "comicid") Long comicId, @RequestBody Comics comic) {
        Comics updatedComic = comicService.updateComic(comicId, comic);
        List<Character> characters = characterService.getComicsCharacters(updatedComic.getId(), new HashMap<>());
        return new ResponseEntity<>(new PersonalComicResRepr(updatedComic, characters), HttpStatus.CREATED);
    }

    @GetMapping("/comics/{comicid}")
    public ResponseEntity<PersonalComicResRepr> getComic(@PathVariable(value = "comicid") Long comicId) {
        Comics comic = comicService.getComicById(comicId);
        List<Character> characters = characterService.getComicsCharacters(comicId, new HashMap<>());
        return new ResponseEntity<>(new PersonalComicResRepr(comic, characters), HttpStatus.OK);
    }

    @DeleteMapping("/comics/{comicid}")
    public ResponseEntity<Comics> deleteComic(@PathVariable(value = "comicid") Long comicId) {
        comicService.deleteComic(comicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
