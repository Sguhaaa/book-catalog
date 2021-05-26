package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping("/book/new")
    public String createBook(Model model) {
        model.addAttribute("appName", "Добавить книгу");
        return "create";
    }

    @PostMapping("/book/new")
    public String doCreate(@ModelAttribute("title") String title, @ModelAttribute("content") String content
            ,@ModelAttribute("author") String author, @ModelAttribute("genres") String genres) {
        bookService.create(title, content, author, genres);
        return "redirect:/books";
    }

    @GetMapping("/book/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        bookService.remove(id);
        return "redirect:/books";
    }

    @GetMapping("book/{id}/edit")
    public String editBook(Model model, @PathVariable("id") Long id){
        Book book = bookService.find(id);
        String genres = book.toString(book.getGenres());
        model.addAttribute("appName", "Изменить данные");
        model.addAttribute("book", book);
        model.addAttribute("genresName", genres);
        return "edit";
    }
    @PostMapping("book/{id}/edit")
    public String doEdit(@ModelAttribute("id") Long id, @ModelAttribute("title") String title,
                         @ModelAttribute("content") String content, @ModelAttribute("author") String author,
                         @ModelAttribute("genresName") String genres) {
        bookService.update(id, title, content, author, genres);
        return "redirect:/books";
    }
}
