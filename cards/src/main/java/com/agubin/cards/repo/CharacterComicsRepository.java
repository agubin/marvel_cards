package com.agubin.cards.repo;

import java.util.List;
import com.agubin.cards.models.CharacterComics;
import org.springframework.data.repository.CrudRepository;

public interface CharacterComicsRepository  extends CrudRepository<CharacterComics, Long> {

    List<CharacterComics> findByCharId(Long id);

    List<CharacterComics> findByComicsId(Long id);

}
