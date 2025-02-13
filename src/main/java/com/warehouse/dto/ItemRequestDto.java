package com.warehouse.dto;

public class ItemRequestDto {
    private Long id;
    private int quantity;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
