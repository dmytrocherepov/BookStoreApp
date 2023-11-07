package com.example.bookstoreapp.repository.shoppingCart;

import java.util.Optional;
import com.example.bookstoreapp.model.ShoppingCart;
import com.example.bookstoreapp.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findShoppingCartByUser(User user);
}
