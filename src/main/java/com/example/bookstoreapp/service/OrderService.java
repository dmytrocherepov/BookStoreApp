package com.example.bookstoreapp.service;

import java.util.List;
import com.example.bookstoreapp.dto.Order.OrderDto;
import com.example.bookstoreapp.dto.Order.OrderItemDto;
import com.example.bookstoreapp.dto.Order.OrderRequestDto;
import com.example.bookstoreapp.dto.Order.UpdateOrderStatusRequestDto;

public interface OrderService {
    OrderDto addOrder(OrderRequestDto orderRequestDto);

    List<OrderDto> getUserOrder();

    OrderDto updateOrder(UpdateOrderStatusRequestDto requestDto);

    OrderItemDto getOrderItems(Long id);

    OrderItemDto getOrderItems(Long orderId , Long id);


}
