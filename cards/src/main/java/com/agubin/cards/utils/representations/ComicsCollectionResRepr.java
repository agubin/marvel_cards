package com.agubin.cards.utils.representations;

import com.agubin.cards.models.Comics;
import com.agubin.cards.utils.LinkManager;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComicsCollectionResRepr {

//    private String allComicsLink;
//    private String selfLink;
    private int numberOfComics;
    private List<ComicShortView> comics;
    private Map<String, URI> relatedURI;

    public ComicsCollectionResRepr(List<Comics> comicsList) {
        this.numberOfComics = comicsList.size();
        this.comics = comicsList.stream().map(ComicShortView::new).collect(Collectors.toList());
        this.relatedURI = new HashMap<>();
        this.relatedURI.put("allComicsURI", LinkManager.getAllComicsURI());
        this.relatedURI.put("allCharactersLink", LinkManager.getAllCharactersURI());
    }

    public ComicsCollectionResRepr(List<Comics> comicsList, Long characterId) {
        this.numberOfComics = comicsList.size();
        this.comics = comicsList.stream().map(ComicShortView::new).collect(Collectors.toList());
        this.relatedURI = new HashMap<>();
        this.relatedURI.put("allComicsURI", LinkManager.getAllComicsURI());
        this.relatedURI.put("allCharactersURI", LinkManager.getAllCharactersURI());
        this.relatedURI.put("TheCharacterComicsURI", LinkManager.getCharacterComicsURI(characterId));
    }

//    public ComicsCollectionResRepr init(List<Comics> comicsList) {
//        this.allComicsLink = LinkManager.getAllComicsLink();
//        this.selfLink = "";
//        this.numberOfComics = comicsList.size();
//        this.collectionComics = comicsList.stream().map(ComicShortView::new).collect(Collectors.toList());
//        return this;
//    }

//    public String getAllComicsLink() {
//        return allComicsLink;
//    }

//    public String getSelfLink() {
//        return selfLink;
//    }

    public int getNumberOfComics() {
        return numberOfComics;
    }

    public List<ComicShortView> getComics() {
        return comics;
    }

    public Map<String, URI> getRelatedURI() {
        return relatedURI;
    }
}
