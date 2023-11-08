package com.example.bookstoreapp.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemRequestDto(
        @NotNull @Positive Long bookId,
        @NotNull @Positive Integer quantity
) {
}
