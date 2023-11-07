package com.example.bookstoreapp.repository.shoppingCart;

import java.util.Optional;
import com.example.bookstoreapp.model.CartItem;
import com.example.bookstoreapp.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findCartItemByIdAndShoppingCart(Long id , ShoppingCart shoppingCart);

}
