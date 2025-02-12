package com.warehouse.controller;

import com.warehouse.model.Item;
import com.warehouse.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
@RequestMapping("/api/inventory/items")
public class InventoryController {
    private final ItemService itemService;

    public InventoryController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.getAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id){
        Item item = itemService.findById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> saveItem(@Validated @RequestBody Item item){
        Item createdItem = itemService.create(item);
        return ResponseEntity.status(201).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Validated @RequestBody Item item){
        item.setId(id);
        Item updated = itemService.update(item);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
