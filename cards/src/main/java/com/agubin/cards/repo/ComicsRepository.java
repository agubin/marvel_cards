package com.agubin.cards.repo;

import com.agubin.cards.models.Comics;
import org.springframework.data.repository.CrudRepository;

public interface ComicsRepository extends CrudRepository<Comics, Long> {
}
