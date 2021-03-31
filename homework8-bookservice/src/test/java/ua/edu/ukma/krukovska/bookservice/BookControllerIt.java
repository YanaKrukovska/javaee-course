package ua.edu.ukma.krukovska.bookservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIt {

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    void savePort(final int port) {
        RestAssured.port = port;
    }

    @Test
    @SneakyThrows
    void searchExistingBook() {
        Book expectedBook = new Book("Flowers for Algernon", "Daniel Keyes", "978-617-12-7611-6");

        Book[] books = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("searchInput", "for")
                .when()
                .get("/book")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Book[].class);

        assertThat(books).hasSize(1);
        assertThat(books[0]).isEqualTo(expectedBook);

    }

    @Test
    @SneakyThrows
    void searchNotExistingBook() {

        Book[] books = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("searchInput", "zebra")
                .when()
                .get("/book")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Book[].class);

        assertThat(books).hasSize(0);

    }

    @Test
    @SneakyThrows
    void createNewBookSuccessfully() {
        Book newBook = new Book("Grapes of Wrath", "John Steinbeck", "978-123-45-7611-9");
        final String requestBody = objectMapper.writeValueAsString(newBook);

        Book createdBook = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .when()
                .post("/book")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Book.class);

        assertThat(createdBook).isEqualTo(newBook);

    }

    @Test
    @SneakyThrows
    void getAllBooks() {
        Book[] receivedBooks = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/book/all")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Book[].class);

        assertThat(receivedBooks).hasSize(3);
        assertThat(Arrays.stream(receivedBooks).map(Book::getTitle)).containsAll(Arrays.asList("Catcher In The Rye",
                "Martin Eden", "Flowers for Algernon"));
    }

}
