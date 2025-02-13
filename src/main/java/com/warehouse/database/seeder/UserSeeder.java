package com.warehouse.database.seeder;

import com.warehouse.enums.RoleEnum;
import com.warehouse.model.Role;
import com.warehouse.model.User;
import com.warehouse.repository.RoleRepository;
import com.warehouse.repository.UserRepository;
import com.warehouse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {

        // Create Roles
//        for (RoleEnum roleEnum : RoleEnum.values()) {
//            roleRepository.findByName(roleEnum.name()).orElseGet(() -> {
//                Role newRole = new Role();
//                newRole.setName(roleEnum.name());
//                return roleRepository.save(newRole);
//            });
//        }

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
            userRepository.save(adminUser);

            // Sync the role to the admin
            roleService.syncUserRoles(adminUser.getId(), List.of(adminRole.getName()));

        }

    }
}
