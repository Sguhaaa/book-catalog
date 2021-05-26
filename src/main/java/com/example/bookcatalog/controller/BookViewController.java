package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

@Controller
public class BookViewController {
    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("appName", "Каталог книг");
        model.addAttribute("books", bookService.listAllBooks());
        return "list";
    }

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.find(id));
        return "book";
    }
    @PostMapping("filterByGenres")
    public String filterByGenres(@RequestParam String filterByGenres, Map<String, Object> model){
        Iterable<Book>books;
        if(filterByGenres != null && !filterByGenres.isEmpty())
            books = bookService
                    .findByGenres(Arrays.asList(filterByGenres.toLowerCase(Locale.ROOT).split(" ")));
        else  books = bookService.listAllBooks();
        model.put("appName", "Поиск по "+filterByGenres);
        model.put("books", books);
        return "list";
    }
}
