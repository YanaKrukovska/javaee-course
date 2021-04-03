package ua.edu.ukma.krukovska.bookservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Response;
import ua.edu.ukma.krukovska.bookservice.persistence.model.User;
import ua.edu.ukma.krukovska.bookservice.service.BookService;
import ua.edu.ukma.krukovska.bookservice.service.UserService;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
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
    public Response<Book> addBook(@RequestBody @Valid Book book, BindingResult result) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            List<String> responseErrors = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toCollection(LinkedList::new));
            return new Response<>(null, responseErrors);
        }

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

    @GetMapping("/book/favourite")
    public String getFavouriteBooks(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("books", user.getFavouriteBooks());
        return "book-favourite";
    }

    @PostMapping("/book/favourite/add")
    public String addFavouriteBook(@RequestParam Integer bookId, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        Book book = bookService.getBookById(bookId);
        user.addFavouriteBook(book);
        userService.updateUser(user);
        return "redirect:/book/favourite";
    }

    @PostMapping("/book/favourite/delete")
    public String deleteFavouriteBook(@RequestParam Integer bookId, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        Book book = bookService.getBookById(bookId);
        user.removeFavouriteBook(book);
        userService.updateUser(user);
        return "redirect:/book/favourite";
    }
}
