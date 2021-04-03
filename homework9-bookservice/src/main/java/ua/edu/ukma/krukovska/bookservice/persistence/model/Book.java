package ua.edu.ukma.krukovska.bookservice.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ua.edu.ukma.krukovska.bookservice.persistence.validators.IsbnConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    @NotBlank(message = "Title can't be empty")
    private String title;

    @Column(name = "author")
    @Size(min = 2, message = "Book author's name can't be shorter than 2")
    private String author;

    @Column(name = "isbn")
    @IsbnConstraint
    private String isbn;

    @ManyToMany(mappedBy = "favouriteBooks")
    @JsonIgnore
    private Set<User> likedByUsers;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

}
