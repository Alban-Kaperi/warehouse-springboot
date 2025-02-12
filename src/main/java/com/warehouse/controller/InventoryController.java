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
@RequestMapping("/api/inventory")
public class InventoryController {
    private final ItemService itemService;

    public InventoryController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = itemService.getAll();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/")
    public ResponseEntity<Item> saveItem(@Validated @RequestBody Item item){
        Item created = itemService.create(item);
        return ResponseEntity.ok(created);
    }




}
