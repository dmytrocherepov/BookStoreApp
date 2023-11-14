package com.example.bookstoreapp.controller;

import java.util.List;
import com.example.bookstoreapp.dto.Order.OrderDto;
import com.example.bookstoreapp.dto.Order.OrderRequestDto;
import com.example.bookstoreapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public OrderDto addOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.addOrder(requestDto);
    }

    @GetMapping
    public List<OrderDto> getOrder() {
        return orderService.getUserOrder();
    }
}
