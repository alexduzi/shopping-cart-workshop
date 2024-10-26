package com.alexduzi.shoppingcart.service;

import com.alexduzi.shoppingcart.model.CartItem;

public interface ICartItemService {
	void addItem(Long cartId, Long productId, int quantity);

	void removeItem(Long cartId, Long productId);

	void updateItemQuantity(Long cartId, Long productId, int quantity);

	CartItem getCartItem(Long cartId, Long productId);
}
