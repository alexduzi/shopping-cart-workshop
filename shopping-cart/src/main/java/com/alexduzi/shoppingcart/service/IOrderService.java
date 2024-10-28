package com.alexduzi.shoppingcart.service;

import java.util.List;

import com.alexduzi.shoppingcart.dto.OrderDto;
import com.alexduzi.shoppingcart.model.Order;

public interface IOrderService {
	Order placeOrder(Long userId);

	Order getOrder(Long orderId);

	List<Order> getUsersOrders(Long userId);

	OrderDto convertToDto(Order order);
}
