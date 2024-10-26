package com.alexduzi.shoppingcart.service;

import java.math.BigDecimal;

import com.alexduzi.shoppingcart.model.Cart;

public interface ICartService {
	Cart getCart(Long id);

	void clearCart(Long id);

	BigDecimal getTotalPrice(Long id);
}
