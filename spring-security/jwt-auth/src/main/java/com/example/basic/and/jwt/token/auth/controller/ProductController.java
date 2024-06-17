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
     *
     * @return a welcome message.
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secured";
    }

    /**
     * Endpoint to retrieve all products.
     * Only accessible by users with the ROLE_ADMIN authority.
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
     * Only accessible by users with the ROLE_USER authority.
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
     *
     * @param userInfo the user information.
     * @return a message indicating the user was added.
     */
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return productService.addUser(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request !");
        }

    }
}
