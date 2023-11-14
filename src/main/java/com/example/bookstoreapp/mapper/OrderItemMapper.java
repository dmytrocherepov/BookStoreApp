package com.example.bookstoreapp.mapper;

import com.example.bookstoreapp.config.MapperConfig;
import com.example.bookstoreapp.dto.Order.OrderItemDto;
import com.example.bookstoreapp.model.CartItem;
import com.example.bookstoreapp.model.Order;
import com.example.bookstoreapp.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.price" , target = "price")
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(source = "book.id" , target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);
}
