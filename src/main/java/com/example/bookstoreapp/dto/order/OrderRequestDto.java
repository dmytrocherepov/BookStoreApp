package com.example.bookstoreapp.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRequestDto(
        @NotNull
        @Size(max = 255)
        String shippingAddress
) {
}
