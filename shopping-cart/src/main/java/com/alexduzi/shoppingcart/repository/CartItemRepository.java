package com.alexduzi.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexduzi.shoppingcart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteAllByCartId(Long id);
}
