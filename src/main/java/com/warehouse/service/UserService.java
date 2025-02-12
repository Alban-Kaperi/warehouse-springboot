package com.warehouse.service;

import com.warehouse.dto.RegisterDto;
import com.warehouse.dto.UserRequestDto;
import com.warehouse.dto.UserResponseDto;
import com.warehouse.enums.RoleEnum;
import com.warehouse.exception.RoleNotFoundException;
import com.warehouse.exception.UserNotFoundException;
import com.warehouse.mapper.UserResponseMapper;
import com.warehouse.model.Role;
import com.warehouse.model.User;
import com.warehouse.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import com.warehouse.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public UserService(UserRepository userRepository, UserResponseMapper userResponseMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.userResponseMapper = userResponseMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<UserResponseDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto createUser(RegisterDto user) {
        // Create new user
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        // Set roles
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(RoleEnum.CLIENT.name()).orElseThrow(() -> new RoleNotFoundException("Role not found")));
        newUser.setRoles(roles);
        return userResponseMapper.toDto(userRepository.save(newUser));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        // Update fields
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(existingUser);

        // Update the roles to the user and get the User with updated fields
        User updatedUser = roleService.syncUserRoles(id, user.getRoles());

        return userResponseMapper.toDto(updatedUser);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userResponseMapper.toDto(user);
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userResponseMapper.toDto(user);
    }

}
