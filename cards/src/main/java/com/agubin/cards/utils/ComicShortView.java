package com.agubin.cards.utils;

import com.agubin.cards.models.Comics;

import java.net.URI;

public class ComicShortView {

    private String title;
    private URI comicURI;

    public ComicShortView(Comics comic) {
        this.title = comic.getTitle();
        this.comicURI = LinkManager.getComicURI(comic.getId());
    }

    public String getTitle() {
        return title;
    }

    public URI getComicURI() {
        return comicURI;
    }
}
