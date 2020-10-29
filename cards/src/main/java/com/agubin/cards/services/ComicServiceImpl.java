package com.agubin.cards.services;


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
        comics = (List<Comics>) SortFilter.sortAndFilter(comics, allQueryParams);
        return comics;
    }

    private boolean saveAndCheckComicEntity(Comics comic) {
        Comics result = comicsRepository.save(comic);
        return result.getId().equals(comic.getId())
                && result.getTitle().equals(comic.getTitle())
                && result.getDescription().equals(comic.getDescription())
                && result.getCreators().equals(comic.getCreators());
    }

    @Override
    public boolean createComic(Comics comic) {
        return saveAndCheckComicEntity(comic);
    }

    @Override
    public boolean updateComic(Comics comic) {
        return saveAndCheckComicEntity(comic);
    }

    @Override
    public Optional<Comics> getComicById(Long comicId) {
        return comicsRepository.findById(comicId);
    }

    @Override
    public boolean deleteComic(Long comicId) {
        comicsRepository.deleteById(comicId);
        return !comicsRepository.findById(comicId).isPresent();
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


    private boolean checkComicsIdList(List<Long> comicsId) {
        for (Long comicId: comicsId) {
            if (!comicsRepository.findById(comicId).isPresent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean bindComicsToCharacter(Long characterId, List<Long> comicsId) {
        if (checkComicsIdList(comicsId)) {
            for (Long comicId : comicsId) {
                characterComicsRepository.save(new CharacterComics(characterId, comicId));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean unbindComicsFromCharacter(Long characterId, List<Long> comicsId) {
        if (checkComicsIdList(comicsId)) {
            for (Long comicId : comicsId) {
                Optional<CharacterComics> characterComics = characterComicsRepository.findByCharIdAndComicsId(characterId, comicId);
                characterComics.ifPresent(charComics -> characterComicsRepository.delete(charComics));
            }
            return true;
        }
        return false;
    }
}
