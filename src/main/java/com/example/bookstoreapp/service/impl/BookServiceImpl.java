package com.example.bookstoreapp.service.impl;

import com.example.bookstoreapp.dto.book.BookDto;
import com.example.bookstoreapp.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapp.dto.book.BookSearchParameters;
import com.example.bookstoreapp.dto.book.CreateBookRequestDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.BookMapper;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.model.Category;
import com.example.bookstoreapp.repository.book.BookRepository;
import com.example.bookstoreapp.repository.book.BookSpecificationBuilder;
import com.example.bookstoreapp.repository.category.CategoryRepository;
import com.example.bookstoreapp.service.BookService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        setCategories(requestDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't get book with id " + id)
                );
    }

    @Override
    public BookDto updateBookById(CreateBookRequestDto requestDto, Long id) {
        bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No such book with id: " + id)
        );
        Book book = bookMapper.toBook(requestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        isBookExistById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findBooksByCategory(Pageable pageable, Long id) {
        return bookRepository.findBooksByCategoriesId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }


    private void setCategories(CreateBookRequestDto requestDto, Book book) {
        Set<Category> categories = requestDto.categoryIds().stream()
                .map(categoryRepository::getReferenceById)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    private void isBookExistById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("No such book with id: " + id);
        }
    }
}
