package com.example.bookstoreapp.dto.shoppingcart;

import java.util.List;

public record CartDto(
        Long id,
        Long userId,
        List<CartItemDto> cartItems
) {
}
