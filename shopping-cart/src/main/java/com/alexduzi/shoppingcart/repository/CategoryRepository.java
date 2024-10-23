package com.alexduzi.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexduzi.shoppingcart.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);
}
