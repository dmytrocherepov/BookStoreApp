package com.example.bookstoreapp.dto.order;

import com.example.bookstoreapp.model.Order.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateOrderStatusRequestDto(
        @NotNull
        @Size(max = 16)
        Status status
) {
}
