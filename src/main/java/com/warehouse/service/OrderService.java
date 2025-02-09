package com.warehouse.service;

import com.warehouse.enums.OrderStatus;
import com.warehouse.model.Order;
import com.warehouse.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create (Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }
}
