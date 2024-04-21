package com.company.project.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {

        return args -> {
            List<User> users = new ArrayList<>();

            for (int i = 1; i <= 3; i++) {
                String email = "test" + i + "@test.com";
                User user = new User(email);
                users.add(user);
            }

            userRepository.saveAll(users);
        };
    }
}
