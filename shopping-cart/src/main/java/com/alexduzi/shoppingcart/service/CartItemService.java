package com.alexduzi.shoppingcart.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Cart;
import com.alexduzi.shoppingcart.model.CartItem;
import com.alexduzi.shoppingcart.model.Product;
import com.alexduzi.shoppingcart.repository.CartItemRepository;
import com.alexduzi.shoppingcart.repository.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemService implements ICartItemService {

	private final CartItemRepository cartItemRepository;

	private final CartRepository cartRepository;

	private final ICartService cartService;

	private final IProductService productService;

	@Override
	public void addItem(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);

		Product product = productService.getProductById(productId);

		CartItem cartItem = getCartItem(cartId, productId);

		if (cartItem.getId() == null) {
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}

		cartItem.setTotalPrice();

		cart.addItem(cartItem);

		cartItemRepository.save(cartItem);

		cartRepository.save(cart);
	}

	@Override
	public void removeItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);

		CartItem itemToRemove = getCartItem(cartId, productId);

		if (itemToRemove.getId() == null) {
			throw new ResourceNotFoundException("Product not found");
		}

		cart.removeItem(itemToRemove);
		cartRepository.save(cart);
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCart(cartId);

		cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
				.ifPresent(item -> {
					item.setQuantity(quantity);
					item.setUnitPrice(item.getProduct().getPrice());
					item.setTotalPrice();
				});
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		cartRepository.save(cart);
	}

	@Override
	public CartItem getCartItem(Long cartId, Long productId) {
		Cart cart = cartService.getCart(cartId);
		return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst()
				.orElse(new CartItem());
	}
}
