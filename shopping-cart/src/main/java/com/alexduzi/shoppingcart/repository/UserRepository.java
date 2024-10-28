package com.alexduzi.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexduzi.shoppingcart.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
}
