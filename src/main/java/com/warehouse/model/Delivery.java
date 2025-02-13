package com.warehouse.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    @ManyToMany
    @JoinTable(name = "delivery_trucks",  // Join table name
            joinColumns = @JoinColumn(name = "delivery_id"), // Foreign key for Delivery
            inverseJoinColumns = @JoinColumn(name = "chassis_number")) // Foreign key for Truck
    private Set<Truck> trucks = new HashSet<>();

    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Set<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(Set<Truck> trucks) {
        this.trucks = trucks;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
