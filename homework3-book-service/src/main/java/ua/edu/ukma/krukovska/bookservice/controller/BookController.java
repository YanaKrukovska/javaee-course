package ua.edu.ukma.krukovska.bookservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;

import java.util.LinkedList;
import java.util.List;

@Controller
public class BookController {

    private static final List<Book> database = initDatabase();

    @GetMapping("/")
    public String showAll(Model model) {
        model.addAttribute("books", database);
        return "book-list";
    }

    @GetMapping("/add-book")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "add-book-form";
    }

    @PostMapping("/add-book")
    public String submitBook(@ModelAttribute Book book) {
        database.add(book);
        return "redirect:/";
    }

    private static List<Book> initDatabase() {
        List<Book> database = new LinkedList<>();
        database.add(new Book("Catcher In The Rye", "J.D. Salinger", "978-966-14-8783-2"));
        database.add(new Book("Martin Eden", "Jack London", "978-617-07-0777-2"));
        database.add(new Book("Flowers for Algernon", "Daniel Keyes", "978-617-12-7611-6"));
        return database;
    }
}
