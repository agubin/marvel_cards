package com.agubin.cards.controllers;


import com.agubin.cards.models.Character;
import com.agubin.cards.models.Comics;
import com.agubin.cards.services.CharacterService;
import com.agubin.cards.services.ComicService;
import com.agubin.cards.utils.ComicsCollectionResRepr;
import com.agubin.cards.utils.PersonalComicResRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ComicController {


    @Autowired
    private ComicService comicService;
    @Autowired
    private CharacterService characterService;

    @GetMapping("/comics")
    public ResponseEntity<ComicsCollectionResRepr> getComics(@RequestParam Map<String, String> allQueryParams) {
        List<Comics> comics = comicService.getComics(allQueryParams);
        return !comics.isEmpty()
                ? new ResponseEntity<>(new ComicsCollectionResRepr(comics), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/comics")
    public ResponseEntity<?> postComic(@RequestBody Comics comic) {
        boolean isComicCreated = comicService.createComic(comic);
        return isComicCreated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/comics")
    public ResponseEntity<?> updateComic(@RequestBody Comics comic) {
        boolean isComicUpdated = comicService.updateComic(comic);
        return isComicUpdated
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/comics/{comicid}")
    public ResponseEntity<PersonalComicResRepr> getComic(@PathVariable(value = "comicid") Long comicId) {
        Optional<Comics> comic = comicService.getComicById(comicId);
        if (comic.isPresent()) {
            List<Character> characters = characterService.getComicsCharacters(comicId, new HashMap<>());
            return new ResponseEntity<>(new PersonalComicResRepr(comic.get(), characters), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/comics/{comicid}")
    public ResponseEntity<Comics> deleteComic(@PathVariable(value = "comicid") Long comicId) {
        boolean isComicDeleted = comicService.deleteComic(comicId);
        return isComicDeleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

//    @GetMapping("/characters/{characterid}/comics")
//    public ResponseEntity<ComicsCollectionResRepr> getCharacterComic(@RequestParam Map<String, String> allQueryParams,
//                                                          @PathVariable(value = "characterid") Long characterId) {
//        List<Comics> comics = comicService.getCharacterComics(characterId, allQueryParams);
//        return !comics.isEmpty()
//                ? new ResponseEntity<>(new ComicsCollectionResRepr(comics, characterId), HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PutMapping("characters/{characterid}/comics")
//    public ResponseEntity<?> bindComics(@PathVariable(value = "characterid") Long characterId, @RequestBody List<Long> comicsId) {
//        boolean areComicsBounded = comicService.bindComicsToCharacter(characterId, comicsId);
//        return areComicsBounded
//                ? new ResponseEntity<>(HttpStatus.CREATED)
//                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    @DeleteMapping("characters/{characterid}/comics/{comicsid}")
//    public ResponseEntity<?> unbindComics(@PathVariable(value = "characterid") Long characterId, @PathVariable(value = "comicsid") List<Long> comicsId) {
//        boolean areComicsUnbounded = comicService.unbindComicsFromCharacter(characterId, comicsId);
//        return areComicsUnbounded
//                ? new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//    }
}
