package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.order.OrderDto;
import com.example.bookstoreapp.dto.order.OrderItemDto;
import com.example.bookstoreapp.dto.order.OrderRequestDto;
import com.example.bookstoreapp.dto.order.UpdateOrderStatusRequestDto;
import com.example.bookstoreapp.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order Management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Creates an order",
            description = "Creates an order within Shopping carr is not empty"
    )
    @PostMapping
    public OrderDto createOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.addOrder(requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Gets user's order",
            description = "Gets user's order"
    )
    @GetMapping
    public List<OrderDto> getOrder(@PageableDefault(size = 5) Pageable pageable) {
        return orderService.getUserOrder(pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Gets all order item by order id",
            description = "Gets all items from order"
    )
    @GetMapping("/{orderId}/items")
    public List<OrderItemDto> getAllOrderItemByOrderId(@PathVariable Long orderId) {
        return orderService.getAllOrderItems(orderId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Updates order status",
            description = "Updates order status"
    )
    @PatchMapping("/{id}")
    public OrderDto updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusRequestDto requestDto) {
        return orderService.updateOrder(requestDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Gets order item",
            description = "Gets order item by id and order id"
    )
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItemByIdAndOrderId(
            @PathVariable Long orderId,
            @PathVariable Long itemId
    ) {
        return orderService.getOrderItemByOrderAndId(orderId, itemId);
    }
}
