package com.agubin.cards.controllers;

import com.agubin.cards.models.PrCard;
import com.agubin.cards.repo.PrCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

//    @Autowired
//    private PrCardRepository prCardRepository;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("name", "Main page");
        return "main_page";
    }

//    @GetMapping("/cards")
//    public String cardsPage(Model model) {
//        Iterable<PrCard> cards = prCardRepository.findAll();
//        model.addAttribute("cards", cards);
//        return "cards_page";
//    }

}
