package com.example.bookstoreapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.bookstoreapp.dto.book.BookDto;
import com.example.bookstoreapp.dto.book.CreateBookRequestDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.BookMapper;
import com.example.bookstoreapp.mapper.impl.BookMapperImpl;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.model.Category;
import com.example.bookstoreapp.repository.book.BookRepository;
import com.example.bookstoreapp.repository.book.BookSpecificationBuilder;
import com.example.bookstoreapp.repository.category.CategoryRepository;
import com.example.bookstoreapp.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import liquibase.pro.packaged.B;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Spy
    private BookMapper bookMapper = new BookMapperImpl();
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getById_ValidData_ShouldReturnValidBookDto() {
        Long id = 1L;

        Book book = new Book();
        book.setId(id);
        book.setTitle("CheckTitle");
        book.setIsbn("1234567890");
        book.setDescription("TestBook");
        book.setPrice(BigDecimal.ZERO);

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.getById(id);

        assertThat(bookDto)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("title", "CheckTitle")
                .hasFieldOrPropertyWithValue("isbn", "1234567890")
                .hasFieldOrPropertyWithValue("description", "TestBook")
                .hasFieldOrPropertyWithValue("price", BigDecimal.ZERO);

    }

    @Test
    void getById_InvalidId_ShouldThrowException() {
        Long id = -10L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Can't get book with id " + id);
    }

    @Test
    void save_ValidData_ShouldReturnValidBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "TestTitle",
                "TestAuthor",
                "1234567890",
                BigDecimal.valueOf(10),
                "TestDescription",
                null,
                List.of(1L, 2L)
        );

        Category category = new Category();
        category.setId(1L);
        Category category1 = new Category();
        category1.setId(2L);

        Book book = new Book();
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());
        book.setDescription(requestDto.description());
        book.setPrice(requestDto.price());
        book.setCoverImage(requestDto.coverImage());
        book.setCategories(Set.of(category, category1));

        when(categoryRepository.getReferenceById(anyLong())).thenReturn(category, category1);
        when(bookRepository.save(book)).thenReturn(book);

        BookDto bookDto = bookService.save(requestDto);

        assertThat(bookDto)
                .hasFieldOrPropertyWithValue("title", "TestTitle")
                .hasFieldOrPropertyWithValue("author", "TestAuthor")
                .hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(10))
                .hasFieldOrPropertyWithValue("isbn", "1234567890")
                .hasFieldOrPropertyWithValue("description", "TestDescription")
                .hasFieldOrPropertyWithValue("coverImage", null)
                .hasFieldOrPropertyWithValue("categoriesIds", List.of(1L, 2L));
    }

    @Test
    void deleteById_ValidId_ShouldDeleteBook() {
        Long id = 1L;

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(bookRepository).deleteById(anyLong());

        bookService.deleteById(anyLong());
    }

    @Test
    void deleteById_NonExistingId_ShouldThrowException() {
        Long id = -1L;

        assertThatThrownBy(() -> bookService.deleteById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No such book with id: " + id);
    }

    @Test
    void updateBookById_ExistingBook_ShouldReturnBookDto() {
        Long id = 1L;

        CreateBookRequestDto request = new CreateBookRequestDto(
                "TestTitle",
                "TestAuthor",
                "1234567890",
                BigDecimal.valueOf(10),
                null,
                null,
                Collections.emptyList()
        );

        Category category = new Category();
        category.setId(1L);
        category.setName("wow");

        Book book = new Book();
        book.setId(1L);
        book.setAuthor(request.author());
        book.setTitle(request.title());
        book.setPrice(request.price());
        book.setIsbn(request.isbn());

        when(bookRepository.save(book)).thenReturn(book);

        BookDto bookDto = bookService.updateBookById(request, id);

        assertThat(bookDto)
                .hasFieldOrPropertyWithValue("title" , "TestTitle")
                .hasFieldOrPropertyWithValue("author" , "TestAuthor")
                .hasFieldOrPropertyWithValue("isbn" , "1234567890")
                .hasFieldOrPropertyWithValue("price" , BigDecimal.valueOf(10))
                .hasFieldOrPropertyWithValue("categoriesIds" , Collections.emptyList());
    }
}
