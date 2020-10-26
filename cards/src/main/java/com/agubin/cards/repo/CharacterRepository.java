package com.agubin.cards.repo;

import com.agubin.cards.models.Character;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Character, Long> {
}
