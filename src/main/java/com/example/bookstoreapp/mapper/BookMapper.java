package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.config.MapperConfig;
import com.example.bookstoreapp.dto.book.BookDto;
import com.example.bookstoreapp.dto.book.BookDtoWithoutCategoryIds;
import com.example.bookstoreapp.dto.book.CreateBookRequestDto;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoriesIds(book.getCategories()
                .stream()
                .map(Category::getId)
                .toList()
        );
    }
}
