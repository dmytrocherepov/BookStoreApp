package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.shoppingcart.CartDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemUpdateDto;
import com.example.bookstoreapp.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart management", description = "Endpoints for managing shopping carts")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Get user cart",
            description = "Gets user cart"
    )
    @GetMapping
    public CartDto getUserCart() {
        return shoppingCartService.getCart();
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Add cart item",
            description = "Adds cart item to shopping cart"
    )
    @PostMapping
    public CartItemDto addCartItem(@RequestBody @Valid CartItemRequestDto requestDto) {
        return shoppingCartService.addCartItem(requestDto);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Update cart item",
            description = "Updates cart item"
    )
    @PutMapping("/cart-items/{id}")
    public CartItemDto updateCartItem(
            @RequestBody @Valid CartItemUpdateDto requestDto,
            @PathVariable @Positive Long id) {
        return shoppingCartService.updateCartItem(requestDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Delete cart item",
            description = "Deletes cart item"
    )
    @DeleteMapping("/cart-items/{id}")
    public void deleteCartItem(@PathVariable @Positive Long id) {
        shoppingCartService.deleteCartItem(id);
    }
}
