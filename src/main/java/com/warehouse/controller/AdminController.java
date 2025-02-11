package com.warehouse.controller;

import com.warehouse.dto.UserResponseDto;
import com.warehouse.mapper.UserResponseMapper;
import com.warehouse.model.User;
import com.warehouse.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('SYSTEM_ADMIN')")
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;

    public AdminController(UserRepository userRepository, UserResponseMapper userResponseMapper) {
        this.userRepository = userRepository;
        this.userResponseMapper = userResponseMapper;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userResponseMapper.toDto(user));
    }


}
