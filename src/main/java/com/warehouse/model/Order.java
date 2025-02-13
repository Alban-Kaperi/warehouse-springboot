package com.warehouse.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.warehouse.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Order Number is required")
    @Column(unique = true)
    private String orderNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Temporal(TemporalType.DATE)
    private Date deadlineDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // help serialize the bidirectional relationship without running into infinite recursion issues
    private List<OrderItem> items = new ArrayList<>();

    // Many Orders can be created by one User
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String declineReason;


    // Usually I would to do it in the service Layer when I create the order, but because it was in the description of the project I did it like this.
    @PrePersist
    public void generateOrderNumberAndSubmittedDate() {
        if (this.orderNumber == null || this.orderNumber.isEmpty()) {
            this.orderNumber = UUID.randomUUID().toString();
        }
        if (this.submittedDate == null) {
            this.submittedDate = new Date();
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User client) {
        this.user = client;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }

}
