package com.warehouse.database.seeder;

import com.warehouse.enums.RoleEnum;
import com.warehouse.model.Role;
import com.warehouse.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) // Ensure the roles are created before the users
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        for (RoleEnum roleEnum : RoleEnum.values()) {
            roleRepository.findByName(roleEnum.name()).orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(roleEnum.name());
                return roleRepository.save(newRole);
            });
        }

    }
}
