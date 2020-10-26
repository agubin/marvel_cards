package com.agubin.cards.controllers;

import com.agubin.cards.models.PrCard;
import com.agubin.cards.repo.PrCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CardController {

    @Autowired
    private PrCardRepository prCardRepository;

    @GetMapping("/cards")
    public String cardsPage(Model model) {
        Iterable<PrCard> cards = prCardRepository.findAll();
        model.addAttribute("cards", cards);
        return "cards_page";
    }

    @GetMapping("/cards/{id}")
    public String cardsDetailsPage(@PathVariable(value = "id") long id, Model model) {
        PrCard prCard = prCardRepository.findById(id).orElseThrow();
        model.addAttribute("card", prCard);
        return "card_details";
    }

    @GetMapping("/cards/add")
    public String GetAddCard(Model model) {
        return "add_card";
    }

    @PostMapping("/cards/add")
    public String PostAddCard(@RequestParam String name, @RequestParam String description, Model model) {
        PrCard prCard = new PrCard(name, description);
        prCardRepository.save(prCard);
        return "redirect:/cards/add";
    }

    @GetMapping("/cards/{id}/edit")
    public String cardEditGet(@PathVariable(value = "id") long id, Model model) {
        PrCard prCard = prCardRepository.findById(id).orElseThrow();
        model.addAttribute("card", prCard);
        return "edit_card";
    }

    @PostMapping("/cards/{id}/edit")
    public String cardEditPost(@PathVariable(value = "id") long id, @RequestParam String name,
                               @RequestParam String description, Model model) {
        PrCard prCard = prCardRepository.findById(id).orElseThrow();
        prCard.setName(name);
        prCard.setDescription(description);
        prCardRepository.save(prCard);
        model.addAttribute("card", prCard);
        return "card_details";
    }

    @PostMapping("/cards/{id}/delete")
    public String cardDeletePost(@PathVariable(value = "id") long id, Model model) {
        prCardRepository.deleteById(id);
//        PrCard prCard = prCardRepository.findById(id).orElseThrow();
//        prCardRepository.delete(prCard);
        return "redirect:/cards";
    }
}
