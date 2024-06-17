package com.example.basic.and.jwt.token.auth.config;

import com.example.basic.and.jwt.token.auth.filter.JwtAuthFilter;
import com.example.basic.and.jwt.token.auth.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for Spring Security.
 * Configures authentication providers, user details service, password encoder, and security filter chain.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    /**
     * Bean to load user-specific data.
     * <p>
     * This method creates and returns an instance of {@link UserInfoUserDetailsService}
     * which implements {@link UserDetailsService}. This service is used to load user-specific
     * data during the authentication process.
     *
     * @return an instance of UserInfoUserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    /**
     * Bean to configure the security filter chain.
     * <p>
     * This method configures web-based security for specific HTTP requests. The order of the
     * request matchers is important because they are evaluated in sequence. Once a match is found,
     * the remaining matchers are not evaluated. Therefore, specific paths should be placed before
     * more general ones.
     * </p>
     * <p>
     * The security filter chain is configured to:
     * <ul>
     *     <li>Disable CSRF protection.</li>
     *     <li>Allow unauthenticated access to specific endpoints.</li>
     *     <li>Require authentication for all other "/product/**" endpoints.</li>
     *     <li>Set session management to stateless to support JWT-based authentication.</li>
     *     <li>Add a custom JWT authentication filter before the {@link UsernamePasswordAuthenticationFilter}.</li>
     * </p>
     *
     * @param http the HttpSecurity object to configure web-based security for specific HTTP requests.
     * @return the SecurityFilterChain object.
     * @throws Exception if an error occurs while building the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) // Disables Cross-Site Request Forgery (CSRF) protection
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/product/welcome").permitAll() // Allows unauthenticated access to "/product/welcome"
                        .requestMatchers("/product/new").permitAll() // Allows unauthenticated access to "/product/new"
                        .requestMatchers("/product/authenticate").permitAll() // Allows unauthenticated access to "/product/authenticate"
                        .requestMatchers("/product/**").authenticated() // Requires authentication for all other "/product/**" endpoints
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sets session management to stateless
                .authenticationProvider(authenticationProvider()) // Configures the custom authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Adds the JWT authentication filter
                .build(); // Builds the SecurityFilterChain object
    }

    /**
     * Bean to encode passwords using BCrypt hashing algorithm.
     * <p>
     * This method creates and returns an instance of {@link BCryptPasswordEncoder}, which is used to encode
     * passwords before storing them and to verify encoded passwords during authentication.
     * </p>
     *
     * @return an instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication provider for the application.
     * <p>
     * The authentication provider is responsible for authenticating users. This method creates a
     * {@link DaoAuthenticationProvider}, sets the {@link UserDetailsService} to retrieve user information,
     * and sets the {@link PasswordEncoder} to verify passwords. The {@code DaoAuthenticationProvider}
     * uses the {@link UserDetailsService} to load user details and the {@link PasswordEncoder} to
     * compare the provided password with the stored hashed password.
     * </p>
     *
     * @return an instance of {@link AuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Creates a new instance of DaoAuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // Sets the UserDetailsService that the DaoAuthenticationProvider will use to load user details
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        // Sets the PasswordEncoder that the DaoAuthenticationProvider will use to encode and verify passwords
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        // Returns the configured DaoAuthenticationProvider instance
        return daoAuthenticationProvider;
    }

    /**
     * Bean to configure the AuthenticationManager.
     * <p>
     * This method creates and returns an instance of {@link AuthenticationManager} using the provided
     * {@link AuthenticationConfiguration}. The AuthenticationManager is used to authenticate users.
     * </p>
     *
     * @param configuration the AuthenticationConfiguration object to configure the AuthenticationManager.
     * @return an instance of AuthenticationManager.
     * @throws Exception if an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
