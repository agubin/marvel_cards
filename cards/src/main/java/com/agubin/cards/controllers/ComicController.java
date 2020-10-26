package com.agubin.cards.controllers;

import com.agubin.cards.models.Char;
import com.agubin.cards.models.CharacterComics;
import com.agubin.cards.models.Comics;
import com.agubin.cards.repo.CharacterComicsRepository;
import com.agubin.cards.repo.ComicsRepository;
import com.agubin.cards.services.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ComicController {

    @Autowired
    private ComicsRepository comicsRepository;
    @Autowired
    private CharacterComicsRepository characterComicsRepository;
    @Autowired
    private ComicService comicService;

    @GetMapping("/comics")
    public String comicsPage(Model model) {
        Iterable<Comics> comics = comicsRepository.findAll();
        model.addAttribute("comics", comics);
        return "comics_page";
    }

    @GetMapping("/comics/{id}")
    public String ComicsDetailsPage(@PathVariable(value = "id") long id, Model model) {
        try {
            Comics comics = comicsRepository.findById(id).orElseThrow();
            model.addAttribute("comics", comics);
            return "comics_details";
        } catch (NoSuchElementException ex){
            System.out.println("I got id " + id);
        }
        return "stub";
    }

    @GetMapping("/characters/{id}/comics")
    public String ComicsChars(@PathVariable(value = "id") long id, Model model) {
        List<CharacterComics> matches = characterComicsRepository.findByCharId(id);
        ArrayList<Comics> comics = new ArrayList<>();
        for (CharacterComics match : matches) {
            comics.add(comicsRepository.findById(match.getComicsId()).orElseThrow());
        }
        model.addAttribute("comics", comics);
        return "comics_page";
    }
}
