package com.cursor.library.controllers;

import com.cursor.library.models.Book;
import com.cursor.library.models.CreateBookDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class BookControllerTest extends BaseControllerTest {

    @Test
     void createBookTest() throws Exception {
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("Cool createBookDto");
        createBookDto.setDescription("Cool description");
        createBookDto.setNumberOfWords(100500);
        createBookDto.setRating(10);
        createBookDto.setYearOfPublication(2020);
        createBookDto.setAuthors(Arrays.asList("author1", "author2"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(createBookDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Book book = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                Book.class
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/books/" + book.getBookId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void getAllBooks() {
        try {
            mockMvc.perform(MockMvcRequestBuilders
            .get("/books"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBookById() throws Exception {
        mockMvc.perform(get("/books/random_id_value_1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value("random_id_value_1"));

    }

    @Test
    void deleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        .delete("/books/random_id_value_5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookId").value("random_id_value_5"));
    }



}
