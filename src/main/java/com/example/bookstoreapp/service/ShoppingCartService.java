package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.shoppingCart.CartDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {
    CartDto getCart();
    CartItemDto addCartItem(CartItemRequestDto requestDto);

    CartItemDto updateCartItem(CartItemUpdateDto requestDto , Long itemId);

    void deleteCartItem(Long id);
}
