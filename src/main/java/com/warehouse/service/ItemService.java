package com.warehouse.service;

import com.warehouse.model.Item;
import com.warehouse.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create (Item item) {
        return itemRepository.save(item);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
