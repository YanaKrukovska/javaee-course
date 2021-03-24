package ua.edu.ukma.krukovska.bookservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE lower(b.title) LIKE %:q% OR lower(b.author) LIKE %:q% OR lower(b.isbn) LIKE %:q%")
    List<Book> findAllWhereTitleLikeOrAuthorLikeOrIsbnLike(@Param("q") String q);
}
