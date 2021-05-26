package com.example.bookcatalog.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "book")
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String author;

    @OneToMany(mappedBy = "book", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name="book_genres",
            joinColumns={@JoinColumn(name="book_id")},
            inverseJoinColumns={@JoinColumn(name="genre_id")}
    )
    private Set<Genre> genres = new HashSet<>();

    public Book(){}

    public Book(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
        this.reservations = null;
    }

    public Book(String title, String content, String author, String genresStr){
        this.title = title;
        this.content = content;
        this.author = author;
        this.genres = parseGenres(genresStr);
        this.reservations = null;
    }

    public Book(String title, String content, String author, Set<Genre> genres){
        this.title = title;
        this.content = content;
        this.author = author;
        this.genres = genres;
        this.reservations = null;
    }

    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor(){return author;}

    public void setAuthor(String author){this.author = author;}

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Set<String> getGenresName() {
        Set<String> result = new HashSet<String>();
        for(var genre : genres){
           result.add(genre.getName());
        }
        return result;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Genre> parseGenres(String genresStr){
        String[] genres = genresStr.split(",");
        HashSet<Genre> result = new HashSet<>();
        for(String genre:genres)
            result.add(new Genre(genre));
        return result;
    }

    public String toString(Set<Genre> genres){
        StringBuilder result = new StringBuilder();
        for(Genre genre:genres){
            result.append(genre.getName()).append(" ");
        }
        return result.toString();
    }
}
