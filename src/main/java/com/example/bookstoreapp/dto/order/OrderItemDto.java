package com.example.bookstoreapp.dto.order;

public record OrderItemDto(
        Long id,
        Long bookId,
        Integer quantity
) {
}
