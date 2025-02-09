package com.warehouse.model;

import com.warehouse.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.UUID;

@Entity
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
    private String deadlineDate;

    // Usually I would do it in the service Layer when I create the order, but because it was in the description of the project I did it like this.
    @PrePersist
    public void generateOrderNumberAndSubmittedDate() {
        if (this.orderNumber == null || this.orderNumber.isEmpty()) {
            this.orderNumber = UUID.randomUUID().toString();
        }
        if (this.submittedDate == null) {
            this.submittedDate = new Date();
        }
    }

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

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
