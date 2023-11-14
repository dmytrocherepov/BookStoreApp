package com.example.bookstoreapp.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.bookstoreapp.dto.Order.OrderDto;
import com.example.bookstoreapp.dto.Order.OrderItemDto;
import com.example.bookstoreapp.dto.Order.OrderRequestDto;
import com.example.bookstoreapp.dto.Order.UpdateOrderStatusRequestDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.mapper.OrderItemMapper;
import com.example.bookstoreapp.mapper.OrderMapper;
import com.example.bookstoreapp.model.CartItem;
import com.example.bookstoreapp.model.Order;
import com.example.bookstoreapp.model.OrderItem;
import com.example.bookstoreapp.model.ShoppingCart;
import com.example.bookstoreapp.model.User;
import com.example.bookstoreapp.repository.Order.OrderItemRepository;
import com.example.bookstoreapp.repository.Order.OrderRepository;
import com.example.bookstoreapp.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookstoreapp.repository.user.UserRepository;
import com.example.bookstoreapp.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository itemRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto addOrder(OrderRequestDto orderRequestDto) {
        User user = getUser();
        ShoppingCart userShoppingCart = findUserShoppingCart();
        Order order = createOrder(user, orderRequestDto);
        order.setOrderItems(setOrderItems(order, userShoppingCart.getCartItems()));
        cartRepository.delete(userShoppingCart);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getUserOrder() {
        User user = getUser();
        List<OrderDto> list = orderRepository.findAllWithOrderItemsByUserId(user.getId())
                .stream()
                .map(orderMapper::toDto)
                .toList();
        return list;
    }

    @Override
    public OrderDto updateOrder(UpdateOrderStatusRequestDto requestDto) {
        return null;
    }

    @Override
    public OrderItemDto getOrderItems(Long id) {
        return null;
    }

    @Override
    public OrderItemDto getOrderItems(Long orderId, Long id) {
        return null;
    }

    private User getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(name).orElseThrow(
                () -> new EntityNotFoundException("No such user")
        );
    }

    private ShoppingCart findUserShoppingCart() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(name).orElseThrow(
                () -> new EntityNotFoundException("No such user")
        );
        return cartRepository.findShoppingCartByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("No such cart with user Id")
        );
    }

    private Order createOrder(User user, OrderRequestDto requestDto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setShippingAddress(requestDto.shippingAddress());
        order.setStatus(Order.Status.NEW);
        order.setTotal(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    private Set<OrderItem> setOrderItems(Order order, Set<CartItem> cartItems) {
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = orderItemMapper.toOrderItem(cartItem);
            orderItem.setOrder(order);
            orderItems.add(itemRepository.save(orderItem));
            order.setTotal(order.getTotal()
                    .add(orderItem.getPrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()))));
        }
        return orderItems;
    }
}
