package com.warehouse.service;

import com.warehouse.exception.ItemNotFoundException;
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
        Item itemToUpdate = itemRepository.findById(item.getId()).orElseThrow(() -> new ItemNotFoundException("Item not found"));

        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        itemToUpdate.setQuantity(item.getQuantity());

        return itemRepository.save(itemToUpdate);
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
    }

    public void delete(Long item) {
        Item itemToDelete = itemRepository.findById(item).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        itemRepository.delete(itemToDelete);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }
}
