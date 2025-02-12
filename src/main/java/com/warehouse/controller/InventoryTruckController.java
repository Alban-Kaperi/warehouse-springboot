package com.warehouse.controller;

import com.warehouse.model.Truck;
import com.warehouse.service.TruckService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
@RequestMapping("/api/inventory/trucks")
public class InventoryTruckController {

    private final TruckService truckService;

    public InventoryTruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks(){
        List<Truck> trucks = truckService.findAll();
        return ResponseEntity.ok(trucks);
    }

    @GetMapping("/{chassis}")
    public ResponseEntity<Truck> getTruckById(@PathVariable String chassis){
        Truck truck = truckService.getByChassis(chassis);
        return ResponseEntity.ok(truck);
    }

    @PutMapping("/{chassis}")
    public ResponseEntity<Truck> updateTruck(@PathVariable String chassis, @Validated @RequestBody Truck truck){
        Truck updateTruck = truckService.update(chassis, truck);
        return ResponseEntity.ok(updateTruck);
    }

}
