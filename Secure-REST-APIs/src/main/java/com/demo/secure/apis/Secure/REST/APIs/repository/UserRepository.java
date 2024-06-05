package com.demo.secure.apis.Secure.REST.APIs.repository;

import com.demo.secure.apis.Secure.REST.APIs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
