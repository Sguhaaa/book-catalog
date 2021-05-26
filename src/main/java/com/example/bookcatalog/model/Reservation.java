package com.example.bookcatalog.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String user;

    private Date date;

    private Boolean reserved;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    public Reservation(){

    }

    public Reservation(String user, Boolean isReserveByUser, Book book) {
        this.user = user;
        date = new Date();
        this.reserved = isReserveByUser;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate(){return date;}

    public Book getBook() {
        return book;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
