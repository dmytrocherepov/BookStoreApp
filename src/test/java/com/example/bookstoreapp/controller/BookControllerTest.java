package com.example.bookstoreapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstoreapp.dto.book.BookDto;
import com.example.bookstoreapp.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.util.List;
import jdk.jfr.Description;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:databases/categories/add-categories.sql")
@Sql(scripts = {
        "classpath:databases/books/delete-books.sql",
        "classpath:databases/categories/delete-categories.sql"},
        executionPhase = AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    private static ObjectMapper objectMapper;

    @BeforeAll
    @SneakyThrows
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @Description("Gets a book with id")
    @Sql(scripts = "classpath:databases/books/add-one-book.sql")
    @WithMockUser(username = "user", roles = "USER")
    void getById_validId_Success() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(get("/books/" + id)
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("title", "test")
                .hasFieldOrPropertyWithValue("author", "test")
                .hasFieldOrPropertyWithValue("isbn", "1234567890")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(1.25));
    }

    @Test
    @Description("Saves a book")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void save_validRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "test",
                "test",
                "1234567890",
                BigDecimal.valueOf(1.25),
                null,
                null,
                List.of(1L)
        );
        MvcResult result = mockMvc.perform(post("/books")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("isbn", requestDto.isbn())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("categoriesIds", requestDto.categoriesIds());
    }

    @Test
    @Description("Updates a book")
    @Sql(scripts = "classpath:databases/books/add-one-book.sql")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBookById_validRequestDto_Success() throws Exception {
        Long id = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "test1",
                "test1",
                "1234567890",
                BigDecimal.valueOf(3.25),
                null,
                null,
                List.of(1L)
        );

        MvcResult result = mockMvc.perform(put("/books/" + id)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class
        );

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("isbn", requestDto.isbn())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("categoriesIds", requestDto.categoriesIds());
    }

    @Test
    @Description("Deletes a book with existing id")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = "classpath:databases/books/add-one-book.sql")
    void deleteById_validId_Success() throws Exception {
        Long id = 1L;
        MvcResult result = mockMvc.perform(delete("/books/" + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
