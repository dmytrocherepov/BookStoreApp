package com.example.bookstoreapp;

import com.example.bookstoreapp.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.bookstoreapp.service.BookService;

import java.math.BigDecimal;

@SpringBootApplication
public class BookStoreAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookStoreAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(BookService bookService) {
			return args ->  {
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
