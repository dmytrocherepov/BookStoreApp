package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.book.BookDto;
import com.example.bookstoreapp.dto.book.BookSearchParameters;
import com.example.bookstoreapp.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto updateBookById(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params, Pageable pageable);
}
