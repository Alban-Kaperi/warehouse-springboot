package com.warehouse.controller;

import com.warehouse.dto.RegisterDto;
import com.warehouse.dto.UserRequestDto;
import com.warehouse.dto.UserResponseDto;
import com.warehouse.model.User;
import com.warehouse.service.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('SYSTEM_ADMIN')")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    private final UserService userService;

    public AdminController( UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody RegisterDto user){
        UserResponseDto savedUser = userService.createUser(user);
        logger.info("User with id: {} saved", savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto user) {
        UserResponseDto updatedUser = userService.updateUser(id, user);
        logger.info("User with id: {} updated", id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        logger.info("User with id: {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}
