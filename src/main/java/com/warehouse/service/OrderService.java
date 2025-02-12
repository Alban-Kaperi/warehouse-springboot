package com.warehouse.service;

import com.warehouse.enums.OrderStatus;
import com.warehouse.exception.ItemNotFoundException;
import com.warehouse.model.Item;
import com.warehouse.model.Order;
import com.warehouse.model.OrderItem;
import com.warehouse.repository.ItemRepository;
import com.warehouse.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    public Order create (Order order) {
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0,8)); // Generate a unique ID for the order number
        order.setSubmittedDate(new Date());
        order.setStatus(OrderStatus.CREATED);
        List<OrderItem> items = new ArrayList<>();

        // Validate that items exist in the database and set the items
        List<OrderItem> validatedItems = new ArrayList<>();
        for (OrderItem orderItem : order.getItems()) {
            itemRepository.findById(orderItem.getItem().getId()).orElseThrow(() -> new ItemNotFoundException("Item not found"));
            validatedItems.add(orderItem); // Add only valid items
        }
        order.setItems(validatedItems);

        return orderRepository.save(order);
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
