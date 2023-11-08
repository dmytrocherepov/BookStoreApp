package com.example.bookstoreapp.dto.shoppingcart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemUpdateDto(
        @NotNull
        @Positive
        Integer quantity) {
}
