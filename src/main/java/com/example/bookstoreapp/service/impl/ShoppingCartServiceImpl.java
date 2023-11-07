package com.example.bookstoreapp.service.impl;

import java.util.HashSet;
import com.example.bookstoreapp.dto.shoppingCart.CartDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemRequestDto;
import com.example.bookstoreapp.dto.shoppingCart.CartItemUpdateDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.CartItemMapper;
import com.example.bookstoreapp.mapper.ShoppingCartMapper;
import com.example.bookstoreapp.model.CartItem;
import com.example.bookstoreapp.model.ShoppingCart;
import com.example.bookstoreapp.model.User;
import com.example.bookstoreapp.repository.book.BookRepository;
import com.example.bookstoreapp.repository.shoppingCart.CartItemRepository;
import com.example.bookstoreapp.repository.shoppingCart.ShoppingCartRepository;
import com.example.bookstoreapp.repository.user.UserRepository;
import com.example.bookstoreapp.service.ShoppingCartService;
import jakarta.transaction.Transactional;
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
    public CartDto getCart() {
        return shoppingCartMapper.toDto(getUserCart());
    }

    @Override
    public CartItemDto addCartItem(CartItemRequestDto requestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.quantity());
        cartItem.setBook(bookRepository.findById(requestDto.bookId()).orElseThrow(
                () -> new EntityNotFoundException("No such book")
        ));
        cartItem.setShoppingCart(getUserCart());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItem(CartItemUpdateDto updateDto, Long itemId) {
        ShoppingCart userCart = getUserCart();
        CartItem cartItem = cartItemRepository.findCartItemByIdAndShoppingCart(itemId, userCart).orElseThrow(
                () -> new EntityNotFoundException("No such cartItem")
        );
        cartItem.setQuantity(updateDto.quantity());
      return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    private ShoppingCart getUserCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("No such user")
        );
       return shoppingCartRepository.findShoppingCartByUser(user).orElseGet(() -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCart.setCartItems(new HashSet<>());
            return shoppingCartRepository.save(shoppingCart);
        });
    }
}
