package com.warehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Item Name is required")
    private String name;

    @NotNull(message = "Item Quantity is required")
    @PositiveOrZero(message = "Item Quantity must be greater than or equal to zero")
    private Integer quantity;

    @NotNull(message = "Item Price is required")
    @PositiveOrZero(message = "Item Price must be greater than or equal to zero")
    private Double price;

    // Default
    public Item() {
    }

    public Item(String name, Integer quantity, Double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
