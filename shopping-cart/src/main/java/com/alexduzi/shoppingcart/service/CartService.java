package com.alexduzi.shoppingcart.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Cart;
import com.alexduzi.shoppingcart.model.CartItem;
import com.alexduzi.shoppingcart.repository.CartItemRepository;
import com.alexduzi.shoppingcart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

	private final CartRepository cartRepository;
	
	private final CartItemRepository cartItemRepository;

	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cartRepository.deleteById(id);
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

}
