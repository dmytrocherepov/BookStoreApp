package com.example.bookstoreapp.repository.order;

import com.example.bookstoreapp.model.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long orderId, Long orderItemId);
}
