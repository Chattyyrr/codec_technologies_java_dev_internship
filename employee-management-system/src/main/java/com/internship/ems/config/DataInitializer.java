package com.internship.ems.config;

import com.internship.ems.model.*;
import com.internship.ems.model.Role;
import com.internship.ems.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner seedUsers(AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            createIfMissing(users, encoder, "admin", "Admin@12345", Role.ADMIN);
            createIfMissing(users, encoder, "hr", "Hr@12345", Role.HR);
            createIfMissing(users, encoder, "viewer", "Viewer@12345", Role.VIEWER);
        };
    }
    private void createIfMissing(AppUserRepository users, PasswordEncoder encoder, String name, String raw, Role role) {
        if(users.findByUsername(name).isEmpty()) {
            AppUser user=new AppUser(); user.setUsername(name);
            user.setPassword(encoder.encode(raw)); user.setRole(role); users.save(user);
        }
    }
}
