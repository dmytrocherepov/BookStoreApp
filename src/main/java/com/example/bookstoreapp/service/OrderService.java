package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.order.OrderDto;
import com.example.bookstoreapp.dto.order.OrderItemDto;
import com.example.bookstoreapp.dto.order.OrderRequestDto;
import com.example.bookstoreapp.dto.order.UpdateOrderStatusRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto addOrder(OrderRequestDto orderRequestDto);

    List<OrderDto> getUserOrder(Pageable pageable);

    OrderDto updateOrder(UpdateOrderStatusRequestDto requestDto, Long id);

    List<OrderItemDto> getAllOrderItems(Long id);

    OrderItemDto getOrderItemByOrderAndId(Long orderId, Long id);
}
