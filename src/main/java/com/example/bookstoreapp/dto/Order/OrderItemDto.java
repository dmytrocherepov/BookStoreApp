package com.example.bookstoreapp.dto.Order;

public record OrderItemDto(
        Long id,
        Long bookId,
        Integer quantity
) {
}
