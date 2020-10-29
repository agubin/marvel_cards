package com.agubin.cards.repo;

import java.util.List;
import java.util.Optional;

import com.agubin.cards.models.CharacterComics;
import org.springframework.data.repository.CrudRepository;

public interface CharacterComicsRepository  extends CrudRepository<CharacterComics, Long> {

    List<CharacterComics> findByCharId(Long id);

    List<CharacterComics> findByComicsId(Long id);

    Long deleteByCharIdAndComicsId(Long characterId, Long comicId);

    Optional<CharacterComics> findByCharIdAndComicsId(Long characterId, Long comicId);
}
