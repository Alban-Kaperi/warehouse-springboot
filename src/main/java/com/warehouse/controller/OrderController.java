package com.warehouse.controller;

import com.warehouse.enums.OrderStatus;
import com.warehouse.model.Order;
import com.warehouse.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> findAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(Long id) {
        return orderService.findById(id) != null ? ResponseEntity.ok(orderService.findById(id)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Order> createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        return ResponseEntity.ok(orderService.create(order));
    }

}
