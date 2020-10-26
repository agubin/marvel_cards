package com.agubin.cards.repo;

import com.agubin.cards.models.Char;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Char, Long> {
}
