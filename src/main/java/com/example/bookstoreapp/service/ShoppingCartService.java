package com.example.bookstoreapp.service;

import com.example.bookstoreapp.dto.shoppingcart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.bookstoreapp.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto getCart();

    CartItemDto addCartItem(CartItemRequestDto requestDto);

    CartItemDto updateCartItem(CartItemUpdateRequestDto requestDto, Long itemId);

    void deleteCartItem(Long id);
}
