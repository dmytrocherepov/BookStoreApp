package com.example.bookstoreapp.service.impl;

import static com.example.bookstoreapp.model.Order.Status.NEW;

import com.example.bookstoreapp.dto.order.OrderDto;
import com.example.bookstoreapp.dto.order.OrderItemDto;
import com.example.bookstoreapp.dto.order.OrderRequestDto;
import com.example.bookstoreapp.dto.order.UpdateOrderStatusRequestDto;
import com.example.bookstoreapp.exception.EntityNotFoundException;
import com.example.bookstoreapp.exception.ShoppingCartException;
import com.example.bookstoreapp.mapper.OrderItemMapper;
import com.example.bookstoreapp.mapper.OrderMapper;
import com.example.bookstoreapp.model.CartItem;
import com.example.bookstoreapp.model.Order;
import com.example.bookstoreapp.model.OrderItem;
import com.example.bookstoreapp.model.ShoppingCart;
import com.example.bookstoreapp.model.User;
import com.example.bookstoreapp.repository.order.OrderItemRepository;
import com.example.bookstoreapp.repository.order.OrderRepository;
import com.example.bookstoreapp.repository.shoppingcart.CartItemRepository;
import com.example.bookstoreapp.repository.shoppingcart.ShoppingCartRepository;
import com.example.bookstoreapp.repository.user.UserRepository;
import com.example.bookstoreapp.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public OrderDto addOrder(OrderRequestDto orderRequestDto) {
        User user = getUser();
        ShoppingCart userShoppingCart = findUserShoppingCart();
        if (userShoppingCart.getCartItems().isEmpty()) {
            throw new ShoppingCartException("Cart is empty");
        }
        Order order = createOrder(user, orderRequestDto);
        order.setOrderItems(setOrderItems(order, userShoppingCart.getCartItems()));
        userShoppingCart.clearCartItems();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getUserOrder(Pageable pageable) {
        User user = getUser();
        return orderRepository.findAllWithOrderItemsByUserId(pageable, user.getId())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public OrderDto updateOrder(UpdateOrderStatusRequestDto requestDto, Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("No such order with id")
        );
        order.setStatus(requestDto.status());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getAllOrderItems(Long id) {
        return itemRepository.findAllByOrderId(id).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemByOrderAndId(Long orderId, Long id) {
        OrderItem orderItem = itemRepository.findByIdAndOrderId(orderId, id).orElseThrow(
                () -> new EntityNotFoundException("No such order item with id" + id)
        );
        return orderItemMapper.toDto(orderItem);
    }

    private User getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(name).orElseThrow(
                () -> new EntityNotFoundException("No such user")
        );
    }

    private ShoppingCart findUserShoppingCart() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getUser();
        return cartRepository.findShoppingCartByUserId(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("No such cart with user Id")
        );
    }

    private Order createOrder(User user, OrderRequestDto requestDto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setShippingAddress(requestDto.shippingAddress());
        order.setStatus(NEW);
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
