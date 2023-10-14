package com.example.bookstoreapp;

import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.service.BookService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreAppApplication {

    private final BookService bookService;

    public BookStoreAppApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Clean Code");
            book.setAuthor("Robert Martin");
            book.setIsbn("1232456");
            book.setPrice(BigDecimal.valueOf(2345));
            book.setDescription("Useful book");
            book.setCoverImage("Image");
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
