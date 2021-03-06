package ua.edu.ukma.krukovska.bookservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private static final List<Book> database = initDatabase();

    @GetMapping("/")
    public String showAll() {
        return "index";
    }

    @GetMapping("/book")
    @ResponseBody
    public List<Book> searchBook(@RequestParam String searchInput) {
        return database.stream().filter(book -> checkSearchParams(searchInput.toLowerCase(), book)).collect(Collectors.toList());
    }

    private boolean checkSearchParams(String searchInputLowercase, Book book) {
        return book.getTitle().toLowerCase().contains(searchInputLowercase) ||
                book.getIsbn().toLowerCase().contains(searchInputLowercase);
    }

    @ResponseBody
    @GetMapping("/book/all")
    public List<Book> getAllBooks() {
        return database;
    }

    @PostMapping("/book")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Book addBook(@RequestBody Book book) {
        database.add(book);

        return book;
    }

    private static List<Book> initDatabase() {
        List<Book> database = new LinkedList<>();
        database.add(new Book("Catcher In The Rye", "J.D. Salinger", "978-966-14-8783-2"));
        database.add(new Book("Martin Eden", "Jack London", "978-617-07-0777-2"));
        database.add(new Book("Flowers for Algernon", "Daniel Keyes", "978-617-12-7611-6"));
        return database;
    }
}
