package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.BookDto;
import com.example.bookstoreapp.dto.BookSearchParameters;
import com.example.bookstoreapp.dto.CreateBookRequestDto;
import com.example.bookstoreapp.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management ", description = "Endpoints for managing books")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(
            summary = "Get all books",
            description = "Get a list of all available books on page with size = 5"
    )
    public List<BookDto> getAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(
            summary = "Update book with id",
            description = "Updates book with id if present"
    )
    public BookDto updateBookById(
            @RequestBody @Valid CreateBookRequestDto requestDto,
            @PathVariable @Positive Long id
    ) {
        return bookService.updateBookById(requestDto, id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    @Operation(
            summary = "Search a book",
            description = "Searches book with parameters"
    )
    public List<BookDto> searchBooks(
            BookSearchParameters searchParameters,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Positive Long id) {
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book with id if present"
    )
    void deleteById(@PathVariable @Positive Long id) {
        bookService.deleteById(id);
    }
}
