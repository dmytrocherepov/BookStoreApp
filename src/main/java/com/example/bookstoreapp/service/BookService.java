package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.BookDto;
import com.example.bookstoreapp.dto.BookSearchParameters;
import com.example.bookstoreapp.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto updateBookById(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters params, Pageable pageable);
}
