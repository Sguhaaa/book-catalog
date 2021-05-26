package com.example.bookcatalog.service;

import com.example.bookcatalog.model.Genre;
import com.example.bookcatalog.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public GenreService(){

    }

    public Genre create(String genreName) {
        genreName = genreName.toLowerCase(Locale.ROOT);
        Genre genre = new Genre(genreName);
        Genre genreFromDB = genreRepository.findByName(genreName);
        if(genreFromDB==null)
            return genreRepository.save(genre);
        return genreFromDB;
    }

    public HashSet<Genre> createGenres(String genresStr) {
        String[] genres = genresStr.split(" ");
        HashSet<Genre> result = new HashSet<>();
        for(String genre:genres)
            result.add(create(genre));
        return result;
    }

    public Iterable<Genre> listAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findGenreById(Long genreId){
        if(genreRepository.findById(genreId).isPresent())
            return genreRepository.findById(genreId).get();
        return null;
    }

    public void remove(Long genreId){
        genreRepository.deleteById(genreId);
    }
}
