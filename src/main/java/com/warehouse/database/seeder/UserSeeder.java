package com.warehouse.database.seeder;

import com.warehouse.model.Role;
import com.warehouse.model.User;
import com.warehouse.repository.RoleRepository;
import com.warehouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Order(2) // Ensure the users are created after the roles
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Check if admin role exists
        Role adminRole = roleRepository.findByName("SYSTEM_ADMIN")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("SYSTEM_ADMIN");
                    return roleRepository.save(newRole);
                });

        // Check if admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setRoles(Set.of(adminRole));

            userRepository.save(adminUser);
        }

    }
}
