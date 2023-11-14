package com.example.bookstoreapp.dto.Order;

import com.example.bookstoreapp.model.Order;

public record UpdateOrderStatusRequestDto(Order.Status status) {
}
