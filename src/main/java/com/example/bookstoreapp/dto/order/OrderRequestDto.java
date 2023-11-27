package com.example.bookstoreapp.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequestDto(
        @NotBlank
        @Size(max = 255)
        String shippingAddress
) {
}
