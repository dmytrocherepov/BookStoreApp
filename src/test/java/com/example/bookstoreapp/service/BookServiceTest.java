package com.example.bookstoreapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstoreapp.dto.book.BookDto;
import com.example.bookstoreapp.dto.book.CreateBookRequestDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.BookMapper;
import com.example.bookstoreapp.mapper.impl.BookMapperImpl;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.model.Category;
import com.example.bookstoreapp.repository.book.BookRepository;
import com.example.bookstoreapp.repository.category.CategoryRepository;
import com.example.bookstoreapp.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import jdk.jfr.Description;
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
    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private BookMapper bookMapper = new BookMapperImpl();

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @Description("Save a book")
    void save_validRequestDto_returnsValidDto() {
        CreateBookRequestDto requestDto = getDefaultRequestDto();

        Category category = new Category();
        category.setId(1L);

        Book book = new Book();
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());
        book.setPrice(requestDto.price());
        book.setDescription(requestDto.description());
        book.setCategories(Set.of(category));
        book.setCoverImage(requestDto.coverImage());

        when(categoryRepository.getReferenceById(1L)).thenReturn(category);
        when(bookRepository.save(any())).thenReturn(book);

        BookDto actual = bookService.save(requestDto);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("isbn", requestDto.isbn())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("categoriesIds", requestDto.categoriesIds())
                .hasFieldOrPropertyWithValue("coverImage", requestDto.coverImage());
    }

    @Test
    @Description("Get a book with existing id")
    void getById_validId_returnsValidDto() {
        Long id = 2L;
        Book book = new Book();
        book.setId(id);
        book.setAuthor("test");
        book.setIsbn("1234567890");
        book.setTitle("test");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        BookDto dto = bookService.getById(id);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("id", book.getId())
                .hasFieldOrPropertyWithValue("author", book.getAuthor())
                .hasFieldOrPropertyWithValue("isbn", book.getIsbn())
                .hasFieldOrPropertyWithValue("title", book.getTitle());
    }

    @Test
    @Description("Get a book with non existing id")
    void getById_invalidId_ShouldThrowException() {
        Long id = -1L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Can't get book with id " + id);
    }

    @Test
    @Description("Updates a book with existing id and valid RequestDto")
    void updateBookById_validRequestDto_returnsValidDto() {
        Long id = 1L;

        CreateBookRequestDto requestDto = getDefaultRequestDto();
        Category category = new Category();
        category.setId(1L);

        Book book = new Book();
        book.setId(id);
        book.setPrice(requestDto.price());
        book.setCategories(Set.of(category));
        book.setDescription(requestDto.description());
        book.setCoverImage(requestDto.coverImage());
        book.setTitle(requestDto.title());
        book.setAuthor(requestDto.author());
        book.setIsbn(requestDto.isbn());

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(categoryRepository.getReferenceById(id)).thenReturn(category);
        when(bookRepository.save(any())).thenReturn(book);

        BookDto dto = bookService.updateBookById(requestDto, id);

        assertThat(dto)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("title", requestDto.title())
                .hasFieldOrPropertyWithValue("author", requestDto.author())
                .hasFieldOrPropertyWithValue("price", requestDto.price())
                .hasFieldOrPropertyWithValue("description", requestDto.description())
                .hasFieldOrPropertyWithValue("categoriesIds", requestDto.categoriesIds());
    }

    @Test
    @Description("Updates a book with non existing id")
    void updateById_invalidId_ShouldThrowException() {
        Long id = -1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto(
                "checkTitle",
                "checkAuthor",
                "1234567890",
                BigDecimal.valueOf(10),
                null,
                null,
                List.of(1L)
        );
        when(bookRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> bookService.updateBookById(requestDto, id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No such book with id: " + id);
    }

    @Test
    @Description("Delete a book with existing id")
    void deleteById_validId_ShouldDoNothing() {
        Long id = 1L;

        when(bookRepository.existsById(id)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(id);

        bookService.deleteById(id);

        verify(bookRepository, times(1)).existsById(id);
        verify(bookRepository, times(1)).deleteById(id);

    }

    @Test
    @Description("Delete a book with not existing id")
    void deleteById_invalidId_ShouldThrowException() {
        Long id = -1L;

        when(bookRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> bookService.deleteById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No such book with id: " + id);

    }

    private CreateBookRequestDto getDefaultRequestDto() {
        return new CreateBookRequestDto(
                "checkTitle",
                "checkAuthor",
                "1234567890",
                BigDecimal.valueOf(10),
                null,
                null,
                List.of(1L)
        );
    }
}
