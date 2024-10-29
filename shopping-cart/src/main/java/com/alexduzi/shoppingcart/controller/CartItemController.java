package com.alexduzi.shoppingcart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Cart;
import com.alexduzi.shoppingcart.model.User;
import com.alexduzi.shoppingcart.response.ApiResponse;
import com.alexduzi.shoppingcart.service.ICartItemService;
import com.alexduzi.shoppingcart.service.ICartService;
import com.alexduzi.shoppingcart.service.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	private final ICartItemService cartItemsService;

	private final ICartService cartService;

	private final IUserService userService;

	@PostMapping("/item/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity) {
		try {
			User user = userService.getUserById(2L);

			Cart cart = cartService.initializeNewCart(user);

			cartItemsService.addItem(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
		try {
			cartItemsService.removeItem(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PutMapping("/cart/{cartId}/item/{itemId}/update")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId,
			@RequestParam Integer quantity) {
		try {
			cartItemsService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
