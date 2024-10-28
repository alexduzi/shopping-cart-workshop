package com.alexduzi.shoppingcart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexduzi.shoppingcart.dto.OrderDto;
import com.alexduzi.shoppingcart.exceptions.ResourceNotFoundException;
import com.alexduzi.shoppingcart.model.Order;
import com.alexduzi.shoppingcart.response.ApiResponse;
import com.alexduzi.shoppingcart.service.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

	private final IOrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
		try {
			Order order = orderService.placeOrder(userId);
			
			OrderDto dto = orderService.convertToDto(order);

			return ResponseEntity.ok(new ApiResponse("Item Order Created!", dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
		try {
			Order order = orderService.getOrder(orderId);
			
			OrderDto dto = orderService.convertToDto(order);

			return ResponseEntity.ok(new ApiResponse("sucess", dto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
		try {
			List<Order> orders = orderService.getUsersOrders(userId);
			
			List<OrderDto> ordersDto = orders.stream().map(orderService::convertToDto).toList();

			return ResponseEntity.ok(new ApiResponse("sucess", ordersDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
