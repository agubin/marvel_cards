package com.agubin.cards.controllers;

import com.agubin.cards.utils.ResourceTypes;
import com.agubin.cards.services.FileService;
import com.agubin.cards.utils.LinkManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

//Handle requests related to files
@RestController
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("characters/{characterid}/portrait")
    public ResponseEntity<?> uploadPortrait(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        fileService.writeDownFile(file, ResourceTypes.CHR_IMG, characterId);
        return ResponseEntity.created(LinkManager.getCharacterPortraitURI(characterId)).build();
    }

    @PutMapping("characters/{characterid}/portrait")
    public ResponseEntity<?> updatePortrait(@PathVariable(value = "characterid") Long characterId,
                                         @RequestParam MultipartFile file) {
        fileService.updateFile(file, ResourceTypes.CHR_IMG, characterId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/characters/{characterid}/portrait")
    public ResponseEntity<?> sendPortrait(@PathVariable(value = "characterid") Long characterId) {
        byte[] bImage = fileService.getFileById(ResourceTypes.CHR_IMG, characterId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentLength(bImage.length).body(new InputStreamResource(new ByteArrayInputStream(bImage)));
    }

    @PostMapping("comic/{comicid}/cover")
    public ResponseEntity<?> uploadComicCover(@PathVariable(value = "comicid") Long comicId,
                                         @RequestParam MultipartFile file) {
        fileService.writeDownFile(file, ResourceTypes.COM_IMG, comicId);
        return ResponseEntity.created(LinkManager.getCharacterPortraitURI(comicId)).build();
    }

    @PutMapping("comic/{comicid}/cover")
    public ResponseEntity<?> updateComicCover(@PathVariable(value = "comicid") Long comicId,
                                         @RequestParam MultipartFile file) {
        fileService.updateFile(file, ResourceTypes.COM_IMG, comicId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("comic/{comicid}/cover")
    public ResponseEntity<?> sendComicCover(@PathVariable(value = "comicid") Long comicId) {
        byte[] bImage = fileService.getFileById(ResourceTypes.COM_IMG, comicId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).contentLength(bImage.length).body(new InputStreamResource(new ByteArrayInputStream(bImage)));
    }
}
