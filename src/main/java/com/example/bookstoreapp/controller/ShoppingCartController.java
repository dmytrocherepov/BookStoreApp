package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.shoppingCart.CartDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemUpdateDto;
import com.example.bookstoreapp.service.ShoppingCartService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public CartDto getUserCart() {
        return shoppingCartService.getCart();
    }

    @PostMapping
    public CartItemDto addCartItem(@RequestBody CartItemRequestDto requestDto) {
        return shoppingCartService.addCartItem(requestDto);
    }

    @PutMapping("/{id}")
    public CartItemDto updateCartItem(@RequestBody CartItemUpdateDto requestDto, @PathVariable @Positive Long id) {
        return shoppingCartService.updateCartItem(requestDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCartItem(@PathVariable Long id) {
        shoppingCartService.deleteCartItem(id);
    }


}
