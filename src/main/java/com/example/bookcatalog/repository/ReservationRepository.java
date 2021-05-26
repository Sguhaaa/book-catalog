package com.example.bookcatalog.repository;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.model.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByBookId(Long id);
}
