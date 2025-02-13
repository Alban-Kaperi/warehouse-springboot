package com.warehouse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "trucks")
public class Truck {

    @Id
    @Column(unique=true)
    private String chassisNumber;

    @NotEmpty(message = "License Plate is required")
    @Column(unique = true)
    private String licensePlate;

    public Truck() {
    }

    public Truck(String chassisNumber, String licensePlate) {
        this.chassisNumber = chassisNumber;
        this.licensePlate = licensePlate;
    }

    // Getters and Setters

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
