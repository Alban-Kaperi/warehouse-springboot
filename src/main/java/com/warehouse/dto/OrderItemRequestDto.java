package com.warehouse.dto;

import jakarta.validation.constraints.NotNull;

public class OrderItemRequestDto {
    private Long id;

    @NotNull(message = "Item ID must not be null")
    private Long itemId;

    @NotNull(message = "Quantity must not be null")
    private int quantity;

    // Default Constructor
    public OrderItemRequestDto() {}

    // Constructor with fields
    public OrderItemRequestDto(Long id, Long itemId, int quantity) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
