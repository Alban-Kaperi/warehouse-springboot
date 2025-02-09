package com.warehouse.service;

import com.warehouse.model.Truck;
import com.warehouse.repository.TruckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {

    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }


    public Truck create(Truck truck) {
        return truckRepository.save(truck);
    }

    public Truck update(Truck truck) {
        return truckRepository.save(truck);
    }

    public void delete(Truck truck) {
        truckRepository.delete(truck);
    }

    public List<Truck> findAll() {
        return truckRepository.findAll();
    }

    public Truck getByChassis(String chassisNumber){
        return truckRepository.findById(chassisNumber).orElse(null);
    }

}
