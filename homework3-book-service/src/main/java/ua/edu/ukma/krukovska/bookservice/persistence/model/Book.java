package ua.edu.ukma.krukovska.bookservice.persistence.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String title;
    private String author;
    private String ISBN;

}
