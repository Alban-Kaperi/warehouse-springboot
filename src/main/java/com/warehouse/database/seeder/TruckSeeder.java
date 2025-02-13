package com.warehouse.database.seeder;

import com.warehouse.model.Truck;
import com.warehouse.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TruckSeeder implements CommandLineRunner {

    @Autowired
    private TruckRepository truckRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Truck> trucks = List.of(
                new Truck("CHASSIS001", "ABC123"),
                new Truck("CHASSIS002", "XYZ789"),
                new Truck("CHASSIS003", "LMN456")
        );

        for (Truck truck : trucks) {
            truckRepository.findById(truck.getChassisNumber())
                    .orElseGet(() -> truckRepository.save(truck));
        }

    }
}
