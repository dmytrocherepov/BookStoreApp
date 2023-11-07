package com.example.bookstoreapp.dto.shoppingCart;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        Integer quantity
) {
}
