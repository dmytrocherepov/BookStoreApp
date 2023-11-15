package com.example.bookstoreapp.dto.order;

import com.example.bookstoreapp.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long userId,
        List<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
