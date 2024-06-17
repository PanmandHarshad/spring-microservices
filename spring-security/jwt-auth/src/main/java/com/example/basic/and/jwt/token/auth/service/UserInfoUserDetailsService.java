package com.example.basic.and.jwt.token.auth.service;

import com.example.basic.and.jwt.token.auth.config.UserInfoUserDetails;
import com.example.basic.and.jwt.token.auth.entity.UserInfo;
import com.example.basic.and.jwt.token.auth.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Custom UserDetailsService implementation to load user-specific data.
 */
@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * Loads the user by their username.
     *
     * @param username the username of the user.
     * @return the UserDetails object.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
        return userInfo.map(UserInfoUserDetails::new).orElseThrow(() ->
                new UsernameNotFoundException("User not found : " + username));
    }
}