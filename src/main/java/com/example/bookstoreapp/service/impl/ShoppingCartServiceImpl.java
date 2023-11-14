package com.example.bookstoreapp.service.impl;

import com.example.bookstoreapp.dto.shoppingcart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingcart.CartItemUpdateRequestDto;
import com.example.bookstoreapp.dto.shoppingcart.ShoppingCartDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.CartItemMapper;
import com.example.bookstoreapp.mapper.ShoppingCartMapper;
import com.example.bookstoreapp.model.Book;
import com.example.bookstoreapp.model.CartItem;
import com.example.bookstoreapp.model.ShoppingCart;
import com.example.bookstoreapp.model.User;
import com.example.bookstoreapp.repository.book.BookRepository;
import com.example.bookstoreapp.repository.shoppingcart.CartItemRepository;
import com.example.bookstoreapp.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookstoreapp.repository.user.UserRepository;
import com.example.bookstoreapp.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final BookRepository bookRepository;
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public ShoppingCartDto getCart() {
        return shoppingCartMapper.toDto(getUserCart());
    }

    @Override
    public CartItemDto addCartItem(CartItemRequestDto requestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.quantity());
        Book book = bookRepository.findById(requestDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("No such book")
        );
        cartItem.setBook(book);
        ShoppingCart userCart = getUserCart();
        cartItem.setShoppingCart(userCart);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Transactional
    @Override
    public CartItemDto updateCartItem(CartItemUpdateRequestDto updateDto, Long itemId) {
        ShoppingCart userCart = getUserCart();
        CartItem cartItem = cartItemRepository
                .findCartItemByIdAndShoppingCart(itemId, userCart)
                .orElseThrow(() -> new EntityNotFoundException("No such cartItem"));
        cartItem.setQuantity(updateDto.quantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Transactional
    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    private ShoppingCart getUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("No such user")
        );
        return shoppingCartRepository.findShoppingCartByUserId(user.getId()).orElseGet(() -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCart.setCartItems(new HashSet<>());
            return shoppingCartRepository.save(shoppingCart);
        });
    }
}
