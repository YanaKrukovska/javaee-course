package ua.edu.ukma.krukovska.bookservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Response;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final EntityManager entityManager;

    @Transactional
    public List<Book> findAllBooks() {
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }


    @Transactional
    public Book getBookByIsbn(String isbn) {
        Book foundBook;
        try {
            foundBook = entityManager.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return foundBook;
    }

    @Transactional
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

        return new Response<>(entityManager.merge(book), errors);
    }

    @Transactional
    public List<Book> searchBook(String searchInput) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE lower(b.title) LIKE :q OR lower(b.author) LIKE :q OR b.isbn LIKE :q",
                Book.class).setParameter("q", "%" + searchInput.toLowerCase() + "%").getResultList();
    }


}
