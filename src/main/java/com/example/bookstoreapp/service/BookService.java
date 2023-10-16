package com.example.bookstoreapp.service;

import com.example.bookstoreapp.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
