package com.example.bookcatalog.service;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.model.Reservation;
import com.example.bookcatalog.repository.BookRepository;
import com.example.bookcatalog.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    BookRepository bookRepository;

    public void createReservation(Book bookId, String user) {
        Reservation reservation = new Reservation(user, true, bookId);
        reservationRepository.save(reservation);
    }

    public void returnBook(Book bookId, String user) {
        Reservation reservation = new Reservation(user, false, bookId);
        reservationRepository.save(reservation);
    }

    public Iterable<Reservation> listAllReservations() {
        return reservationRepository.findAll();
    }

    public Iterable<Reservation> findBookReservations(Long book){
        return reservationRepository.findByBookId(book);
    }

    public Reservation findLastBookReservation(Long book){
        List<Reservation> reservations = reservationRepository.findByBookId(book);
        if(reservations.size()==0 &&  bookRepository.findById(book).isPresent())
            return new Reservation("user", false, bookRepository.findById(book).get());
        return reservations.get(reservations.size()-1);
    }
}
