package com.agubin.cards.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class CharsController {

//    @Autowired
//    private CharacterRepository characterRepository;
//    @Autowired
//    private CharacterComicsRepository characterComicsRepository;
//
//    @GetMapping("/characters")
//    public String charsPage(@RequestParam(required = false) String orderBy,
//                            @RequestParam(required = false) String nameContain, Model model) {
//        List<Char> chars = new ArrayList<>();
//        characterRepository.findAll().forEach(chars::add);
//        if (nameContain != null) {
//            chars = filterBySnippet(chars, nameContain);
//        }
//        if (orderBy != null) {
//            chars.sort(Char.getComparatorFor(orderBy));
//        }
//        model.addAttribute("chars", chars);
//        return "chars_page";
//    }
//
//    private List<Char> filterBySnippet(List<Char> chars, String nameContain) {
//        ArrayList<Char> filteredChars = new ArrayList<>();
//        for (Char character : chars) {
//            if (character.getName().contains(nameContain)) {
//                filteredChars.add(character);
//            }
//        }
//        return filteredChars;
//    }
//
//    @GetMapping("/characters/{id}")
//    public String CharDetailsPage(@PathVariable(value = "id") long id, Model model) {
//        try {
//            Char character = characterRepository.findById(id).orElseThrow();
//            model.addAttribute("character", character);
//            return "character_details";
//        } catch (NoSuchElementException ex){
//            System.out.println("I got id " + id);
//        }
//        return "stub";
//    }
//
//    @GetMapping("/comics/{id}/characters")
//    public String CharsComics(@PathVariable(value = "id") long id, Model model) {
//        List<CharacterComics> matches = characterComicsRepository.findByComicsId(id);
//        ArrayList<Char> chars = new ArrayList<>();
//        for (CharacterComics match : matches) {
//            chars.add(characterRepository.findById(match.getCharId()).orElseThrow());
//        }
//        model.addAttribute("chars", chars);
//        return "chars_page";
//    }





//
//    @GetMapping("/cards/add")
//    public String GetAddCard(Model model) {
//        return "add_card";
//    }
//
//    @PostMapping("/cards/add")
//    public String PostAddCard(@RequestParam String name, @RequestParam String description, Model model) {
//        PrCard prCard = new PrCard(name, description);
//        charsRepository.save(prCard);
//        return "redirect:/cards/add";
//    }
//
//    @GetMapping("/cards/{id}/edit")
//    public String cardEditGet(@PathVariable(value = "id") long id, Model model) {
//        PrCard prCard = charsRepository.findById(id).orElseThrow();
//        model.addAttribute("card", prCard);
//        return "edit_card";
//    }
//
//    @PostMapping("/cards/{id}/edit")
//    public String cardEditPost(@PathVariable(value = "id") long id, @RequestParam String name,
//                               @RequestParam String description, Model model) {
//        PrCard prCard = charsRepository.findById(id).orElseThrow();
//        prCard.setName(name);
//        prCard.setDescription(description);
//        charsRepository.save(prCard);
//        model.addAttribute("card", prCard);
//        return "card_details";
//    }
//
//    @PostMapping("/cards/{id}/delete")
//    public String cardDeletePost(@PathVariable(value = "id") long id, Model model) {
//        charsRepository.deleteById(id);
//        return "redirect:/cards";
//    }
}
