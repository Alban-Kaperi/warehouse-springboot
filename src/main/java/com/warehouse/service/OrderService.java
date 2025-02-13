package com.warehouse.service;

import com.warehouse.dto.ItemRequestDto;
import com.warehouse.dto.OrderItemRequestDto;
import com.warehouse.dto.OrderRequestDto;
import com.warehouse.enums.OrderStatus;
import com.warehouse.exception.ItemNotFoundException;
import com.warehouse.exception.UserNotFoundException;
import com.warehouse.model.Item;
import com.warehouse.model.Order;
import com.warehouse.model.OrderItem;
import com.warehouse.model.User;
import com.warehouse.repository.ItemRepository;
import com.warehouse.repository.OrderItemRepository;
import com.warehouse.repository.OrderRepository;
import com.warehouse.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order create (OrderRequestDto orderRequest) {

        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found")); // Mund edhe ta heqim orElseThrow because it's supposed that the users are authenticated

        // Create Order
        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8)); // Generate a unique ID for the order number
        order.setDeadlineDate(orderRequest.getDeadlineDate()); // add 10 days for current date for the deadline
        order.setOrderNumber(OrderStatus.CREATED.name());
        order.setUser(user);

        List<OrderItem> orderItems = new ArrayList<>();

        // Process items and verify that they exist
        for (OrderItemRequestDto itemRequest : orderRequest.getItems()) {

            // Fetch the item
            Item item = itemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(() -> new ItemNotFoundException("Item not found: ID " + itemRequest.getItemId()));

            // Check if there is enough quantity
            if (item.getQuantity() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for item: " + item.getName());
            }

            // Update item quantity
            item.setQuantity(item.getQuantity() - itemRequest.getQuantity());
            itemRepository.save(item);

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setOrder(order); // Establish parent-child relation
            orderItems.add(orderItem);

        }

        // Attach OrderItems to Order
        order.setItems(orderItems);

        return orderRepository.save(order); // CascadeType.ALL that we have int the Order model ensures orderItems are saved as well

    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }
}
