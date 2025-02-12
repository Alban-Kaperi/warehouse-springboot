package com.warehouse.service;

import com.warehouse.model.Role;
import com.warehouse.model.User;
import com.warehouse.repository.RoleRepository;
import com.warehouse.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    /*
    * - The `Transactional` annotation ensures that the entire method is executed within a transactional context.
    * - If any error occurs during the execution, all changes made to the database are rolled back.
    */
    @Transactional
    public User syncUserRoles(Long userId, List<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert role names to Role entities, creating missing ones
        Set<Role> updatedRoles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(roleName)))) // Create if not exists
                .collect(Collectors.toSet());

        // Overwrite user's existing roles with the new ones
        user.setRoles(updatedRoles);
        return userRepository.save(user);
    }

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

}
