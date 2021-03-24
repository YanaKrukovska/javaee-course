package ua.edu.ukma.krukovska.bookservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;
import ua.edu.ukma.krukovska.bookservice.service.BookService;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Response;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String showAll() {
        return "index";
    }

    @GetMapping("/book")
    @ResponseBody
    public List<Book> searchBook(@RequestParam String searchInput) {
        return bookService.searchBook(searchInput);
    }

    @PostMapping("/book")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Response<Book> addBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @ResponseBody
    @GetMapping("/book/all")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/book/{isbn}")
    public String getBook(@PathVariable String isbn, Model model) {
        Book foundBook = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", foundBook);
        return "book-page";
    }

}
