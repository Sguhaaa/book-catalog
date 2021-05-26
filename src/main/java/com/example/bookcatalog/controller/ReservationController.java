package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import com.example.bookcatalog.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @Autowired
    BookService bookService;

    @GetMapping("/book/{id}/history/new")
    public String create(Model model,@PathVariable("id") Long id) {
        if(reservationService.findLastBookReservation(id).getReserved()){
            model.addAttribute("actionName", "Удалить бронь");
            return "createReservation";
        }
        model.addAttribute("actionName", "Добавить бронь");
        return "createReservation";
    }

    @PostMapping("/book/{id}/history/new")
    public String changeReserveStatus(@ModelAttribute("user") String user, @PathVariable("id") Long id) {
        Book book = bookService.find(id);
        if(reservationService.findLastBookReservation(id).getReserved()){
            reservationService.returnBook(book, user);
        }
        else reservationService.createReservation(book, user);
        return "redirect:/book/{id}/history";
    }



    @GetMapping("/book/{id}/history")
    public String showBookHistory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("appName", "История бронирования");
        model.addAttribute("id", id);
        model.addAttribute("reservations", reservationService.findBookReservations(id));
        return "bookHistory";
    }
}
