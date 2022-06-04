package com.fenrir.masterdetail.loader;

import com.fenrir.masterdetail.dto.RegisterRequest;
import com.fenrir.masterdetail.model.Role;
import com.fenrir.masterdetail.model.User;
import com.fenrir.masterdetail.repository.UserRepository;
import com.fenrir.masterdetail.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class InitialUserDataLoader implements CommandLineRunner {
    private UserRepository userRepository;
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        userService.registerUser(
                new RegisterRequest("kowalski123", "kowalski@gmail.com", "password123")
        );
        userService.registerUser(
                new RegisterRequest("admin", "admin@admin.com", "password123")
        );
        User admin = userRepository.findByUsername("admin").get();
        admin.setRole(Role.ROLE_ADMIN);
        userRepository.save(admin);
    }
}
