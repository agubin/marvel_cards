package com.agubin.cards.utils;

import com.agubin.cards.services.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanBox {

    @Autowired
    public ComicService comicService;
}
