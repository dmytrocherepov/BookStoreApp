package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.shoppingcart.CartDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {
    CartDto getCart();

    CartItemDto addCartItem(CartItemRequestDto requestDto);

    CartItemDto updateCartItem(CartItemUpdateDto requestDto, Long itemId);

    void deleteCartItem(Long id);
}
