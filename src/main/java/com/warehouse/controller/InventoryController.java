package com.warehouse.controller;

import com.warehouse.model.Item;
import com.warehouse.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final ItemService itemService;

    public InventoryController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<Item> saveItem(@Validated @RequestBody Item item){
        Item created = itemService.create(item);
        return ResponseEntity.ok(created);
    }
}
