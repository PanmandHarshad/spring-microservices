package com.example.basic.and.jwt.token.auth.service;

import com.example.basic.and.jwt.token.auth.dto.Product;
import com.example.basic.and.jwt.token.auth.entity.UserInfo;
import com.example.basic.and.jwt.token.auth.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service to manage products and user information.
 */
@Service
public class ProductService {
    List<Product> productList = null;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initializes product list with dummy data after bean construction.
     */
    @PostConstruct
    public void loadProductsFromDB() {
        productList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product " + i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000))
                        .build()
                ).collect(Collectors.toList()); // Generates a list of 100 dummy products
    }

    /**
     * Retrieves all products.
     *
     * @return a list of all products.
     */
    public List<Product> getProducts() {
        return productList;
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product.
     * @return the product with the specified ID.
     */
    public Product getProduct(int id) {
        return productList.stream()
                .filter(product -> product.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Product " + id + " not found"));
    }

    /**
     * Adds a new user to the system.
     *
     * @param userInfo the user information.
     * @return a message indicating the user was added.
     */
    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword())); // Encodes the user's password
        userInfoRepository.save(userInfo); // Saves the user information to the repository
        return "User added to system";
    }
}