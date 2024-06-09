package com.demo.secure.apis.Secure.REST.APIs.config;

import com.demo.secure.apis.Secure.REST.APIs.entity.User;
import com.demo.secure.apis.Secure.REST.APIs.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Initializes data for the application.
 * This class uses CommandLineRunner to insert initial user data into the database.
 */
@Configuration
public class DataInitializer {

    /**
     * Initializes user data using a CommandLineRunner.
     *
     * @param userRepository  UserRepository for user data access
     * @param passwordEncoder PasswordEncoder for encoding passwords
     * @return CommandLineRunner instance for data initialization
     */
    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Save a user with username 'user' and encoded password 'password' to the database
            userRepository.save(new User(null, "user", passwordEncoder.encode("password")));
        };
    }
}
