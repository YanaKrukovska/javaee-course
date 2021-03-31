package ua.edu.ukma.krukovska.bookservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Response;
import ua.edu.ukma.krukovska.bookservice.persistence.repository.BookRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book getBookById(Integer id){
        return bookRepository.getById(id);
    }

    public Response<Book> createBook(Book book) {

        List<String> errors = new LinkedList<>();

        if (book.getTitle().isBlank() || book.getAuthor().isBlank() || book.getIsbn().isBlank()) {
            errors.add("Empty field");
            return new Response<>(book, errors);
        }

        if (getBookByIsbn(book.getIsbn()) != null) {
            errors.add("Book with such ISBN already exists");
            return new Response<>(book, errors);
        }

        return new Response<>(bookRepository.save(book), errors);
    }

    public List<Book> searchBook(String searchInput) {
        return bookRepository.findAllWhereTitleLikeOrAuthorLikeOrIsbnLike(searchInput.toLowerCase());
    }

}
