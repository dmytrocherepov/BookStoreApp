package com.example.bookstoreapp.repository.order;

import com.example.bookstoreapp.model.Order;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "user", "orderItems.book"})
    List<Order> findAllWithOrderItemsByUserId(Pageable pageable, Long id);
}
