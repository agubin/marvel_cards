package com.agubin.cards.services;


import com.agubin.cards.exceptions.InvalidEntityException;
import com.agubin.cards.exceptions.ResourceNotFoundException;
import com.agubin.cards.exceptions.ResourceTypes;
import com.agubin.cards.exceptions.UnexpectedBehaviourException;
import com.agubin.cards.models.CharacterComics;
import com.agubin.cards.models.Comics;
import com.agubin.cards.repo.CharacterComicsRepository;
import com.agubin.cards.repo.ComicsRepository;
import com.agubin.cards.utils.SortFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComicServiceImpl implements ComicService {

    @Autowired
    private ComicsRepository comicsRepository;
    @Autowired
    private CharacterComicsRepository characterComicsRepository;

    @Override
    public List<Comics> getComics(Map<String, String> allQueryParams) {
        List<Comics> comics = new ArrayList<>();
        comicsRepository.findAll().forEach(comics::add);
        if (comics.isEmpty()) {
            throw new UnexpectedBehaviourException();
        }
        comics = (List<Comics>) SortFilter.sortAndFilter(comics, allQueryParams);
        return comics;
    }

    private Comics saveComicEntity(Comics comic) {
        Comics result = comicsRepository.save(comic);
        return result;
    }

    private void checkComicEntity(Comics comic) throws InvalidEntityException {
        StringBuffer errorMessage = new StringBuffer();
        if (comic.getTitle() == null) {
            errorMessage.append("Title field is required!");
        }
        if (comic.getDescription() == null) {
            errorMessage.append("Description field is required!");
        }
        throw new InvalidEntityException(errorMessage.toString());
    }

    @Override
    public Comics createComic(Comics comic) {
        checkComicEntity(comic);
        return saveComicEntity(comic);
    }

    @Override
    public Comics updateComic(Long comicId, Comics comic) {
        Optional<Comics> comicObj = comicsRepository.findById(comicId);
        if(!comicObj.isPresent()) {
            throw new ResourceNotFoundException(ResourceTypes.COM, comicId);
        }
        Comics updatedComic = comicObj.get();
        if (comic.getTitle() != null) {
            updatedComic.setTitle(comic.getTitle());
        }
        if (comic.getDescription() != null) {
            updatedComic.setDescription(comic.getDescription());
        }
        if (comic.getCreators() != null) {
            updatedComic.setCreators(comic.getCreators());
        }
        return saveComicEntity(updatedComic);
    }

    @Override
    public Comics getComicById(Long comicId) {
        Optional<Comics> comic  = comicsRepository.findById(comicId);
        if (!comic.isPresent()) {
            throw new ResourceNotFoundException(ResourceTypes.COM, comicId);
        }
        return comic.get();
    }

    @Override
    public void deleteComic(Long comicId) {
        if (!comicsRepository.existsById(comicId)) {
            throw new ResourceNotFoundException(ResourceTypes.COM, comicId);
        }
        try {
            comicsRepository.deleteById(comicId);
        } catch (Exception exception) {
            throw new UnexpectedBehaviourException();
        }
    }

    @Override
    public List<Comics> getCharacterComics(Long characterId, Map<String, String> allQueryParams) {
        List<Comics> characterComics = new ArrayList<>();
        for (CharacterComics cc: characterComicsRepository.findByCharId(characterId)) {
            Optional<Comics> comic = comicsRepository.findById(cc.getComicsId());
            comic.ifPresent(characterComics::add);
        }
        characterComics = (List<Comics>) SortFilter.sortAndFilter(characterComics, allQueryParams);
        return characterComics;
    }
}
