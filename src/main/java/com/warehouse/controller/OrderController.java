package com.warehouse.controller;

import com.warehouse.dto.OrderRequestDto;
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
    public ResponseEntity<Order> createOrder(OrderRequestDto order) {
        return ResponseEntity.ok(orderService.create(order));
    }

    // TODO: For not having enough time to finish all the requirements
    // CLIENT: Update order (allowed if status is CREATED or DECLINED)

    // CLIENT: Cancel an order if not already fulfilled, under delivery, or canceled

    // CLIENT: Submit an order (status changes to AWAITING_APPROVAL)

    // CLIENT: View his orders (optionally filtered by status)

    // WAREHOUSE_MANAGER: View all orders (optionally filtered by status)

    // WAREHOUSE_MANAGER: Approve an order

    // WAREHOUSE_MANAGER: Decline an order



}
