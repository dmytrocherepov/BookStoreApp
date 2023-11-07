package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.config.MapperConfig;
import com.example.bookstoreapp.dto.shoppingCart.CartItemDto;
import com.example.bookstoreapp.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(source = "book.id" , target = "bookId")
    @Mapping(source = "book.title" , target = "bookTitle")
    CartItemDto toDto(CartItem cartItem);
}
