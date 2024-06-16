package com.demo.secure.apis.Secure.REST.APIs.service;

import com.demo.secure.apis.Secure.REST.APIs.entity.User;
import com.demo.secure.apis.Secure.REST.APIs.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of UserDetailsService for loading user-specific data.
 * This class retrieves user details from the database and creates UserDetails objects.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user-specific data from the database and creates a UserDetails object.
     *
     * @param username User's username
     * @return UserDetails object containing user details
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user from the database by username
        User user = userRepository.findByUsername(username);
        if (user == null) {
            // Throw exception if user not found
            throw new UsernameNotFoundException("User not found");
        }
        // Return a UserDetails object containing user details
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), Collections.emptyList()
        );
    }
}
