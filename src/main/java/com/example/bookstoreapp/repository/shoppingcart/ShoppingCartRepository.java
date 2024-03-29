package com.example.bookstoreapp.repository.shoppingcart;

import com.example.bookstoreapp.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findShoppingCartByUserId(Long id);
}
