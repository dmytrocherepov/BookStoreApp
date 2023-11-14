package com.example.bookstoreapp.repository.Order;

import java.util.List;
import java.util.Optional;
import com.example.bookstoreapp.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
   @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
   List<Order> findAllWithOrderItemsByUserId(Long id);
}
