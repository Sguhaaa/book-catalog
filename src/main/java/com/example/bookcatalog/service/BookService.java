package com.example.bookcatalog.service;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.model.Genre;
import com.example.bookcatalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreService genreService;

    public BookService(){

    }

    public void create(String title, String content,String author, String genres) {
        Set<Genre> bookGenres = genreService.createGenres(genres);
        Book book = new Book(title, content, author, bookGenres);
        bookRepository.save(book);
    }

    public Iterable<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public Book find(Long bookId){
        if(bookRepository.findById(bookId).isPresent())
            return bookRepository.findById(bookId).get();
        return null;
    }

    public Iterable<Book> findByGenres(List<String> genres){
        Set<Book> result =new HashSet<>();
        Iterable<Book> allBooks = bookRepository.findAll();
        for(Book book:allBooks){
            Set<String> genreNames = new HashSet<>();
            for(Genre genre: book.getGenres())
                genreNames.add(genre.getName().toLowerCase(Locale.ROOT));
            if(genreNames.containsAll(genres))
                result.add(book);
        }
        return result;
    }

    public Iterable<Book> findByGenreId(Long genreId){
        Set<Book> result =new HashSet<>();
        Iterable<Book> allBooks = bookRepository.findAll();
        for(Book book:allBooks){
            Set<Long> bookGenresId = new HashSet<>();
            for(Genre genre: book.getGenres())
                bookGenresId.add(genre.getId());
            if(bookGenresId.contains(genreId))
                result.add(book);
        }
        return result;
    }

    public void remove(Long bookId){
        if(bookRepository.findById(bookId).isPresent()){
            Book book = bookRepository.findById(bookId).get();
            for(Genre genre: book.getGenres()){
                HashSet<Book> books = (HashSet<Book>) findByGenreId(genre.getId());
                if(books.size()==1)
                    genreService.remove(genre.getId());
            }
            bookRepository.deleteById(bookId);
        }
    }

    public void update(Long bookId, String title, String content, String author, String genres){
        if(bookRepository.findById(bookId).isPresent()) {
            Book book = bookRepository.findById(bookId).get();
            book.setTitle(title);
            book.setContent(content);
            book.setAuthor(author);
            book.setGenres(book.parseGenres(genres));
            bookRepository.save(book);
        }
    }
}
