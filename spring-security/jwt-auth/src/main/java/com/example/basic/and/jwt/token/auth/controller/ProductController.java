package com.example.basic.and.jwt.token.auth.controller;

import com.example.basic.and.jwt.token.auth.dto.AuthRequest;
import com.example.basic.and.jwt.token.auth.dto.Product;
import com.example.basic.and.jwt.token.auth.entity.UserInfo;
import com.example.basic.and.jwt.token.auth.service.JwtService;
import com.example.basic.and.jwt.token.auth.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint to welcome users.
     * <p>
     * This endpoint is accessible without authentication and simply returns a welcome message.
     * </p>
     *
     * @return a welcome message.
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secured";
    }

    /**
     * Endpoint to retrieve all products.
     * <p>
     * This endpoint is secured and only accessible by users with the ROLE_ADMIN authority.
     * </p>
     *
     * @return a list of all products.
     */
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts() {
        return productService.getProducts();
    }

    /**
     * Endpoint to retrieve a product by its ID.
     * <p>
     * This endpoint is secured and only accessible by users with the ROLE_USER authority.
     * </p>
     *
     * @param id the ID of the product.
     * @return the product with the specified ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return productService.getProduct(id);
    }

    /**
     * Endpoint to add a new user.
     * <p>
     * This endpoint allows adding a new user without requiring authentication. The user information
     * is passed in the request body and saved using the {@link ProductService}.
     * </p>
     *
     * @param userInfo the user information.
     * @return a message indicating the user was added.
     */
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return productService.addUser(userInfo);
    }

    /**
     * Endpoint to authenticate a user and generate a JWT token.
     * <p>
     * This endpoint accepts an {@link AuthRequest} containing the username and password. It uses
     * the {@link AuthenticationManager} to authenticate the user and, if successful, generates a JWT token
     * using the {@link JwtService}.
     * </p>
     *
     * @param authRequest the authentication request containing username and password.
     * @return a JWT token if authentication is successful.
     * @throws UsernameNotFoundException if the authentication request is invalid.
     */
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request !");
        }
    }
}
