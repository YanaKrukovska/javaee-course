package ua.edu.ukma.krukovska.bookservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.edu.ukma.krukovska.bookservice.controller.BookController;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Book;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void addBookSuccess() {
        Book newBook = new Book("Grapes of Wrath", "John Steinbeck", "978-123-45-7611-9");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/book")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Grapes of Wrath")));
    }

    @Test
    @SneakyThrows
    void searchBookSuccess() {
        final String expectedResponse = objectMapper.writeValueAsString(Collections.singletonList(new Book("Flowers for Algernon",
                "Daniel Keyes", "978-617-12-7611-6")));
        mockMvc.perform(
                get("/book")
                        .param("searchInput", "for")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));

    }

    @Test
    @SneakyThrows
    void searchBookEmpty() {
        mockMvc.perform(
                get("/book")
                        .param("searchInput", "zebra")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Collections.emptyList())));

    }

    @Test
    @SneakyThrows
    void getAllBooks() {
        mockMvc.perform(
                get("/book/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
