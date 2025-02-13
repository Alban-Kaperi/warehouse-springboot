package com.warehouse.database.seeder;

import com.warehouse.model.Item;
import com.warehouse.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemSeeder implements CommandLineRunner {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void run(String... args) throws Exception {

        List<Item> items = List.of(
                new Item("Cement - Type I", 500, 7.0),
                new Item("Steel Rebar - 10mm", 125, 25.1),
                new Item("Bricks - Red Clay", 10000, 35.5),
                new Item("Grout - Non-Shrink (50lb bag)", 25, 18.99),
                new Item("Tiles - Ceramic - White", 5000, 1.50),
                new Item("Labor - Demolition", 40, 50.00),
                new Item("2x4 Lumber - 8ft", 200, 4.50)
        );

        for (Item item : items) {
            itemRepository.findByName(item.getName()).ifPresentOrElse(existingItem -> {
                // Update existing item
                existingItem.setQuantity(item.getQuantity());
                existingItem.setPrice(item.getPrice());
                itemRepository.save(existingItem);
            }, () -> {
                // Insert new item
                itemRepository.save(item);
            });
        }

    }
}
