package com.example.basic.and.jwt.token.auth.config;

import com.example.basic.and.jwt.token.auth.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom UserDetails implementation to wrap UserInfo entity.
 */
public class UserInfoUserDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    /**
     * Constructor to initialize UserInfoUserDetails from UserInfo.
     *
     * @param userInfo the UserInfo entity to wrap.
     */
    public UserInfoUserDetails(UserInfo userInfo) {
        this.name = userInfo.getName();
        this.password = userInfo.getPassword();
        authorities = Arrays.stream(userInfo.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()); // Splits roles by comma and converts to GrantedAuthority list
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username.
     */
    @Override
    public String getUsername() {
        return name;
    }
}
