package com.example.bookstoreapp.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.example.bookstoreapp.model.Order;
import com.example.bookstoreapp.model.OrderItem;

public record OrderDto(
        Long id,
        Long userId,
        List<OrderItemDto> orderItems,
        LocalDateTime orderDate,
        BigDecimal total,
        Order.Status status
) {
}
