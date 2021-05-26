package com.example.bookcatalog.repository;

import com.example.bookcatalog.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre findByName(String name);
}
